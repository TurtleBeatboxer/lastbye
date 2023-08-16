import { HttpClient } from '@angular/common/http';
import { AfterContentInit, AfterViewInit, ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild, ViewChildren } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NbStepComponent, NbStepperComponent } from '@nebular/theme';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { ProfileFormService } from './profile-form.service';
import { Router } from '@angular/router';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { profileFormData1 } from './profile-form.model';

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

  profileForm1 = this.profileFormService.buildForm1();
  profileForm2 = this.profileFormService.buildForm2();
  profileForm3 = this.profileFormService.buildForm3();
  profileForm4 = this.profileFormService.buildForm4();

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
        console.log(results[i]);
        if (i === this.accountService.userIdentity.levelOfForm) {
          results[i].select();
        } else {
          currentStep._completed = true;
        }
      }
    }

    this.cd.detectChanges();
  }

  onTest(): void {
    console.log(this.user);
    console.log(this.burialType);
    console.log(this.urn);
    console.log(this.profileForm2.getRawValue());
  }

  onSkip(): void {
    this.router.navigate(['/']);
  }

  onCoffinClick() {
    this.profileForm2.patchValue({ burialMethod: 'coffin' });
    this.otherCremation = false;
    this.otherCremationInput = '';
  }

  onOtherClicked() {
    this.profileForm2.patchValue({ burialMethod: '' });
    this.otherCremation = false;
    this.otherCremationInput = '';
  }

  onCremationClick() {
    this.otherCremation = false;
    this.otherCremationInput = '';
  }

  otherCremationFalse() {
    this.otherCremation = false;

    this.otherCremationInput = '';
  }

  onOtherCremationClick() {
    this.profileForm2.patchValue({ burialMethod: '' });
  }

  submit() {
    console.log(this.profileForm2.getRawValue());
  }

  secondStep(): void {
    this.http
      .post(
        this.applicationConfigService.getEndpointFor('/api/register/form'),
        this.profileFormService.dataProfile2(this.profileForm2.getRawValue())
      )
      .subscribe(() => console.warn(this.profileForm2.getRawValue()));
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
    this.nbStepper.next();
    this.router.navigate(['/']);
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
}
