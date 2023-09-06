import { HttpClient } from '@angular/common/http';
import { AfterContentInit, AfterViewInit, ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild, ViewChildren } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NbStepComponent, NbStepperComponent } from '@nebular/theme';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { ProfileFormService } from './profile-form.service';
import { Router } from '@angular/router';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { Files, Relative, profileFormData1 } from './profile-form.model';

@Component({
  selector: 'jhi-profile-form',
  templateUrl: './profile-form.component.html',
  styleUrls: ['./profile-form.component.scss'],
})
export class ProfileFormComponent implements AfterViewInit, OnInit {
  success: any;
  @ViewChild(NbStepperComponent) nbStepper;
  @ViewChildren(NbStepComponent) nbSteps;
  otherCremation = false;
  user: Account | null = null;
  burialType;
  otherCremationInput;
  urn;
  relatives: Relative[] = [];
  files: Files = { graveProfilePicture: null, farewellLetter: null, videoSpeech: null, testament: null };
  profileForm1 = this.profileFormService.buildForm1();
  profileForm2 = this.profileFormService.buildForm2();
  profileForm3 = this.profileFormService.buildForm3();
  profileForm4 = this.profileFormService.buildForm4();
  profileForm5 = this.profileFormService.buildForm5();

  constructor(
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService,
    private router: Router,
    private profileFormService: ProfileFormService,
    private accountService: AccountService,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    if (this.accountService.userIdentity) {
      this.user = this.accountService.userIdentity;
    }
  }

  ngAfterViewInit(): void {
    const results = this.nbSteps._results;
    if (this.accountService.userIdentity) {
      for (let i = 0; i <= this.accountService.userIdentity.levelOfForm; i++) {
        const currentStep = results[i];
        if (i === this.accountService.userIdentity.levelOfForm) {
          results[i].select();
        } else {
          currentStep._completed = true;
        }
      }
    }
    this.cd.detectChanges();
  }

  onSkip(): void {
    this.router.navigate(['/']);
  }

  onCoffinClick(): void {
    this.profileForm2.patchValue({ burialMethod: 'coffin' });
    this.otherCremation = false;
    this.otherCremationInput = '';
    this.profileForm2.reset();
    this.profileForm2.patchValue({ burialMethod: 'coffin' });
  }

  onOtherClicked(): void {
    this.profileForm2.patchValue({ burialMethod: '' });
    this.otherCremation = false;
    this.otherCremationInput = '';
    this.profileForm2.reset();
  }

  onCremationClick(): void {
    this.otherCremation = false;
    this.otherCremationInput = '';
    this.profileForm2.reset();
    this.profileForm2.patchValue({ burialMethod: 'cremation' });
  }

  otherCremationFalse(): void {
    this.otherCremation = false;

    this.otherCremationInput = '';
  }

  onOtherCremationClick(): void {
    this.profileForm2.patchValue({ burialMethod: '' });
  }

  firstStep(): void {
    this.http
      .post(
        this.applicationConfigService.getEndpointFor('/api/register/form'),
        this.profileFormService.dataProfile1(this.profileForm1.getRawValue())
      )
      .subscribe(() => console.warn(this.profileForm1.getRawValue()));

    this.router.navigate(['user/picture'], { skipLocationChange: true });
  }

  secondStep(): void {
    this.http
      .post(
        this.applicationConfigService.getEndpointFor('/api/register/form'),
        this.profileFormService.dataProfile2(this.profileForm2.getRawValue())
      )
      .subscribe(() => console.warn(this.profileForm2.getRawValue()));
    this.profileFormService.savePhoto(this.files.graveProfilePicture);
    this.nbStepper.next();
  }

  thirdStep(): void {
    this.http
      .post(
        this.applicationConfigService.getEndpointFor('/api/register/form'),
        this.profileFormService.dataProfile3(this.profileForm3.getRawValue())
      )
      .subscribe(() => console.warn(this.profileForm3.getRawValue()));
    this.nbStepper.next();
  }

  fourthStep(): void {
    this.http
      .post(
        this.applicationConfigService.getEndpointFor('/api/register/form'),
        this.profileFormService.dataProfile4(this.profileForm4.getRawValue())
      )
      .subscribe(() => console.warn(this.profileForm4.getRawValue()));
    this.profileFormService.savePhoto(this.files.farewellLetter);
    this.profileFormService.savePhoto(this.files.testament);
    this.profileFormService.savePhoto(this.files.videoSpeech);
    this.nbStepper.next();
  }

  fiveStep(): void {
    const data = this.profileForm5.getRawValue();
    const relative = new Relative(data.email, data.name, data.phone);
    this.relatives.push(relative);
  }

  submitStep5(): void {}

  onFileSelected(event: Event): void {
    console.log(this.files);
    this.files = this.profileFormService.onFileSelected(event, this.files);
    console.log(this.files);
  }
}
