import { Injectable } from '@angular/core';
import { Files, profileFormData1, profileFormData2, profileFormData3, profileFormData4, smallFile } from './profile-form.model';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ProfileFormService {
  constructor(
    private accountService: AccountService,
    private applicationConfigService: ApplicationConfigService,
    private http: HttpClient
  ) {}

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
      burialType: new FormControl('', {
        nonNullable: true,
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
      }),
      burialMethod: new FormControl('', { nonNullable: true }),
      burialPlace: new FormControl('', {
        nonNullable: true,
      }),
      ifGraveInscription: new FormControl(false, { nonNullable: true, validators: [Validators.required] }),
      graveInscription: new FormControl('', {
        nonNullable: true,
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
      }),
      ifPhotoGrave: new FormControl(false, { nonNullable: true, validators: [Validators.required] }),
      openCoffin: new FormControl(false, { nonNullable: true, validators: [Validators.required] }),

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
      // obituary: new FormControl('', {
      //   nonNullable: true,
      //   validators: [
      //     Validators.required,
      //     Validators.minLength(1),
      //     Validators.maxLength(50),
      //     Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      //   ],
      // }),
      // obituaryText: new FormControl(''),
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
      ifFarewellLetter: new FormControl(null, { nonNullable: true, validators: [Validators.required] }),
      farewellLetter: new FormControl(null, { nonNullable: true, validators: [Validators.required] }),
      farewellReader: new FormControl(''),
      ifVideoSpeech: new FormControl(null, { nonNullable: true, validators: [Validators.required] }),
      videoSpeech: new FormControl(null, { nonNullable: true, validators: [Validators.required] }),
      ifTestament: new FormControl(null, { nonNullable: true, validators: [Validators.required] }),
      testament: new FormControl(null, { nonNullable: true, validators: [Validators.required] }),
      ifOther4: new FormControl(null),
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

  dataProfile2(form: profileFormData2, burialType: string) {
    return { ...form, ...this.getUserId(), levelOfForm: 1, burialType };
  }

  dataProfile3(form: profileFormData3): profileFormData3 {
    return { ...form, ...this.getUserId(), levelOfForm: 2 };
  }

  dataProfile4(form: profileFormData4): profileFormData4 {
    return { ...form, ...this.getUserId(), levelOfForm: 3 };
  }

  dataProfile5(relativeDTOs: any) {
    return { relativeDTOs, ...this.getUserId(), levelOfForm: 4 };
  }

  savePhoto(file: smallFile | null): void {
    if (file) {
      const formData = new FormData();

      formData.append('file', file.file);
      formData.append('type', file.name);
      if (this.accountService.userIdentity) {
        formData.append('user', this.accountService.userIdentity.userId.toString());
      }
      const upload$ = this.http.post(this.applicationConfigService.getEndpointFor('/api/profile/pictures'), formData);

      upload$.subscribe(res => {
        console.log(res);
      });
    }
  }

  onFileSelected(event: Event, files: Files): Files {
    const target = event.target as HTMLInputElement;
    const fileList: FileList | null = target.files;
    if (fileList) {
      switch (target.name) {
        case 'graveProfilePicture':
          files.graveProfilePicture = new smallFile(fileList[0], target.name);
          return files;

        case 'farewellLetter':
          files.farewellLetter = new smallFile(fileList[0], target.name);
          return files;
        case 'videoSpeech':
          files.videoSpeech = new smallFile(fileList[0], target.name);
          return files;
        case 'testament':
          files.testament = new smallFile(fileList[0], target.name);
          return files;
        default:
          return files;
      }
    }
    return files;
  }
}
