/* eslint-disable @typescript-eslint/explicit-function-return-type */
/* eslint-disable @typescript-eslint/member-ordering */
import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NbStepperComponent } from '@nebular/theme';
import { AccountService } from 'app/core/auth/account.service';
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
  sendRegister() {}
  @ViewChild(NbStepperComponent) nbStepper;

  constructor(
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService,
    private router: Router,
    private profileFormService: ProfileFormService
  ) {}

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

  secondStep() {
    this.http
      .post(
        this.applicationConfigService.getEndpointFor('/api/register/form'),
        this.profileFormService.dataProfile2(this.profileForm2.getRawValue())
      )
      .subscribe(() => console.info(this.profileForm2.getRawValue()));
    this.nbStepper.next();
  }

  thirdStep() {
    this.http
      .post(
        this.applicationConfigService.getEndpointFor('/api/register/form'),
        this.profileFormService.dataProfile3(this.profileForm3.getRawValue())
      )
      .subscribe(() => console.info(this.profileForm3.getRawValue()));
    this.nbStepper.next();
  }

  fourthStep() {
    this.http
      .post(
        this.applicationConfigService.getEndpointFor('/api/register/form'),
        this.profileFormService.dataProfile4(this.profileForm4.getRawValue())
      )
      .subscribe(() => console.info(this.profileForm4.getRawValue()));
    this.nbStepper.next();
    this.router.navigate(['/']);
  }

  firstStep() {
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
