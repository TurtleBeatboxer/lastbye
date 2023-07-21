import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { LANGUAGES } from 'app/config/language.constants';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from 'app/core/config/application-config.service';

// const this.initialAccount: Account = {} as Account;

@Component({
  selector: 'jhi-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss'],
})
export class SettingsComponent implements OnInit {
  success = false;
  languages = LANGUAGES;
  isTrueCoffin: boolean;
  editsError = false;
  initialAccount: Account = {} as Account;
  edits: number;
  fileName = '';
  settingsForm = new FormGroup({
    firstName: new FormControl(this.initialAccount.firstName, {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
    }),
    lastName: new FormControl(this.initialAccount.lastName, {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
    }),
    email: new FormControl(this.initialAccount.email, {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email],
    }),
    prefix: new FormControl(this.initialAccount.prefix, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    phone: new FormControl<number>(this.initialAccount.phone, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    burialMethod: new FormControl(this.initialAccount.burialMethod, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    graveInscription: new FormControl(this.initialAccount.graveInscription, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    openCoffin: new FormControl(this.initialAccount.openCoffin, { nonNullable: true, validators: [Validators.required] }),
    clothes: new FormControl(this.initialAccount.clothes, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    flowers: new FormControl(this.initialAccount.flowers, {
      nonNullable: true,
      validators: [Validators.required],
    }),
    ifFlowers: new FormControl(this.initialAccount.ifFlowers, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    obituary: new FormControl(this.initialAccount.obituary, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    spotify: new FormControl(this.initialAccount.spotify, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    guests: new FormControl(this.initialAccount.guests, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    notInvited: new FormControl(this.initialAccount.notInvited, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    placeOfCeremony: new FormControl(this.initialAccount.placeOfCeremony, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    farewellLetter: new FormControl(this.initialAccount.farewellLetter, { nonNullable: true, validators: [Validators.required] }),
    videoSpeech: new FormControl(this.initialAccount.videoSpeech, { nonNullable: true, validators: [Validators.required] }),
    testament: new FormControl(this.initialAccount.testament, { nonNullable: true, validators: [Validators.required] }),
    other: new FormControl(this.initialAccount.other, { nonNullable: true, validators: [Validators.required] }),
    authorities: new FormControl(this.initialAccount.authorities, { nonNullable: true }),
    imageUrl: new FormControl(this.initialAccount.imageUrl, { nonNullable: true }),
    login: new FormControl(this.initialAccount.login, { nonNullable: true }),
    userId: new FormControl(this.initialAccount.userId, { nonNullable: true }),
    langKey: new FormControl(this.initialAccount.langKey, { nonNullable: true }),
    levelOfForm: new FormControl(this.initialAccount.levelOfForm, { nonNullable: true }),
    activated: new FormControl(this.initialAccount.activated, { nonNullable: true }),
    editsLeft: new FormControl(this.initialAccount.editsLeft, { nonNullable: true }),
  });

  constructor(
    public accountService: AccountService,
    private translateService: TranslateService,
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.edits = account.editsLeft;
        console.log(account.openCoffin);
        this.settingsForm.patchValue(account);

        console.log(this.isTrueCoffin);
        console.log(this.initialAccount);
      }
    });
  }
  onFileSelected(event) {
    const file: File = event.target.files[0];

    if (file) {
      this.fileName = file.name;

      const formData = new FormData();

      formData.append('thumbnail', file);

      const upload$ = this.http.post('/api/thumbnail-upload', formData);

      upload$.subscribe();
    }
  }

  save(): void {
    this.success = false;

    const account = this.settingsForm.getRawValue();
    this.accountService.save(account).subscribe(res => {
      if (res === 'OK') {
        this.success = true;
        this.editsError = false;
      } else {
        this.success = false;
        this.editsError = true;
      }
      this.accountService.authenticate(account);

      if (account.langKey !== this.translateService.currentLang) {
        this.translateService.use(account.langKey);
      }
    });
  }
  onClick() {
    console.log(this.initialAccount);
  }
}
