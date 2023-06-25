import { HttpClient } from '@angular/common/http';
import { Component, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NbStepperComponent } from '@nebular/theme';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { ProfileFormService } from './profile-form.service';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-profile-form',
  templateUrl: './profile-form.component.html',
  styleUrls: ['./profile-form.component.scss'],
})
export class ProfileFormComponent {
  success: any;
  @ViewChild(NbStepperComponent) nbStepper;

  profileForm1 = new FormGroup({
    firstName: new FormControl('', {
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
    phoneNumber: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
  });

  profileForm2 = new FormGroup({
    burialMethod: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),

    graveInscription: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    openCoffin: new FormControl(false, { nonNullable: true, validators: [Validators.required] }),

    clothes: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
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
    private profileFormService: ProfileFormService
  ) {}

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
    // if(!this.accountService.userIdentity){
    //   return
    // }
    // const form = this.profileForm2.getRawValue();
    // this.http
    //   .post(this.applicationConfigService.getEndpointFor('/api/register/form'), {
    //     userId: this.accountService.userIdentity.userId,
    //     login: this.accountService.userIdentity.login,
    //     burialMethod: form.burialMethod,
    //     graveInscription: form.graveInscription,
    //     isOpenCoffin: form.
    //     clothes: 'dsad',
    //     levelOfForm: 1,
    //   })
    //   // eslint-disable-next-line no-console
    //   .subscribe(res => console.log(res));
    this.nbStepper.next();
  }
}
