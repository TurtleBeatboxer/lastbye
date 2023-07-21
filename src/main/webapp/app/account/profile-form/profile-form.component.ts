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
  burialMethod: any;
  user: Account | null = null;
  burialType;

  profileForm1 = new FormGroup({
    firstName: new FormControl(this.user?.firstName ?? 'hello', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    lastName: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    prefix: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    phone: new FormControl<number>(null!, {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
    }),
  });
  profileForm2 = new FormGroup({
    burialMethod: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
    }),

    graveInscription: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
    }),
    openCoffin: new FormControl(false, { nonNullable: true, validators: [Validators.required] }),

    clothes: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
    }),
  });

  profileForm3 = new FormGroup({
    flowers: new FormControl(false, {
      nonNullable: true,
      validators: [Validators.required],
    }),
    ifFlowers: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    obituary: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    spotify: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    guests: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    notInvited: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    placeOfCeremony: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
  });

  test = new FormGroup({
    burial: new FormControl(''),
  });

  profileForm4 = new FormGroup({
    farewellLetter: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    videoSpeech: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    testament: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    other: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
  });

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
  }

  onSkip(): void {
    this.router.navigate(['/']);
  }

  onCoffinClick() {
    this.burialType = 'coffin';
  }

  onOtherClicked() {
    this.burialType = 'other';
    this.test.setValue({ burial: '' });
  }

  onCremationClick() {
    this.burialType = 'cremation';
  }

  submit() {
    console.log(this.test.getRawValue());
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
    this.nbStepper.next();
  }
}
