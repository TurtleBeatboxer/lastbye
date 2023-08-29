import { Injectable } from '@angular/core';
import { profileFormData1, profileFormData2, profileFormData3, profileFormData4 } from './profile-form.model';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Injectable({
  providedIn: 'root',
})
export class ProfileFormService {
  constructor(private accountService: AccountService) {}

  getUserId(): { userId: number; login: string } {
    if (this.accountService.userIdentity) {
      return { userId: this.accountService.userIdentity.userId, login: this.accountService.userIdentity.login };
    } else {
      return { userId: -1, login: '' };
    }
  }

  buildForm1() {
    return new FormGroup({
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
      phone: new FormControl<number>(null!, {
        nonNullable: true,
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
      }),
    });
  }

  buildForm2() {
    return new FormGroup({
      burialMethod: new FormControl('', {
        nonNullable: true,
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
      }),
      burialPlace: new FormControl('', {
        nonNullable: true,
      }),
      ifGraveInscription: new FormControl(null),
      graveInscription: new FormControl('', {
        nonNullable: true,
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
      }),
      ifPhoto: new FormControl(null),
      photoGrave: new FormControl(''),
      openCoffin: new FormControl(null, { nonNullable: true, validators: [Validators.required] }),

      clothes: new FormControl('', {
        nonNullable: true,
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
      }),
    });
  }

  buildForm3() {
    return new FormGroup({
      flowers: new FormControl(null, {
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
      obituaryText: new FormControl(''),
      musicType: new FormControl(''),
      spotify: new FormControl('', {
        nonNullable: true,
        validators: [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(50),
          Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
        ],
      }),
      ifGuests: new FormControl(null),
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
  }

  buildForm4() {
    return new FormGroup({
      ifFarewell: new FormControl(null),
      farewellLetter: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
      farewellReader: new FormControl(''),
      ifVideoSpeech: new FormControl(null),
      videoSpeech: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
      ifTestament: new FormControl(null),
      testament: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
      ifOther: new FormControl(null),
      other: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    });
  }

  buildForm5() {
    return new FormGroup({
      name: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
      email: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
      phone: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    });
  }
  // }

  dataProfile1(form: profileFormData1): profileFormData1 {
    console.log({ ...form, ...this.getUserId(), levelOfForm: 0 });
    return { ...form, ...this.getUserId(), levelOfForm: 0 };
  }

  dataProfile2(form: profileFormData2): profileFormData2 {
    return { ...form, ...this.getUserId(), levelOfForm: 1 };
  }

  dataProfile3(form: profileFormData3): profileFormData3 {
    return { ...form, ...this.getUserId(), levelOfForm: 2 };
  }

  dataProfile4(form: profileFormData4): profileFormData4 {
    return { ...form, ...this.getUserId(), levelOfForm: 3 };
  }
}
