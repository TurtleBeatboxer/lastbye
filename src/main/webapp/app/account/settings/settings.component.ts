import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { LANGUAGES } from 'app/config/language.constants';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from 'app/core/config/application-config.service';

const initialAccount: Account = {} as Account;

@Component({
  selector: 'jhi-settings',
  templateUrl: './settings.component.html',
})
export class SettingsComponent implements OnInit {
  success = false;
  languages = LANGUAGES;
  isTrueCoffin: boolean;

  settingsForm = new FormGroup({
    firstName: new FormControl(initialAccount.firstName, {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
    }),
    lastName: new FormControl(initialAccount.lastName, {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
    }),
    email: new FormControl(initialAccount.email, {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email],
    }),
    prefix: new FormControl(initialAccount.prefix, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    phone: new FormControl<number>(initialAccount.phone, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    burialMethod: new FormControl(initialAccount.burialMethod, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    graveInscription: new FormControl(initialAccount.graveInscription, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    openCoffin: new FormControl(initialAccount.openCoffin, { nonNullable: true, validators: [Validators.required] }),
    clothes: new FormControl(initialAccount.clothes, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    flowers: new FormControl(initialAccount.flowers, {
      nonNullable: true,
      validators: [Validators.required],
    }),
    ifFlowers: new FormControl(initialAccount.ifFlowers, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    obituary: new FormControl(initialAccount.obituary, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    spotify: new FormControl(initialAccount.spotify, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    guests: new FormControl(initialAccount.guests, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    notInvited: new FormControl(initialAccount.notInvited, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    placeOfCeremony: new FormControl(initialAccount.placeOfCeremony, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    farewellLetter: new FormControl(initialAccount.farewellLetter, { nonNullable: true, validators: [Validators.required] }),
    videoSpeech: new FormControl(initialAccount.videoSpeech, { nonNullable: true, validators: [Validators.required] }),
    testament: new FormControl(initialAccount.testament, { nonNullable: true, validators: [Validators.required] }),
    other: new FormControl(initialAccount.other, { nonNullable: true, validators: [Validators.required] }),
    authorities: new FormControl(initialAccount.authorities, { nonNullable: true }),
    imageUrl: new FormControl(initialAccount.imageUrl, { nonNullable: true }),
    login: new FormControl(initialAccount.login, { nonNullable: true }),
    userId: new FormControl(initialAccount.userId, { nonNullable: true }),
    langKey: new FormControl(initialAccount.langKey, { nonNullable: true }),
    levelOfForm: new FormControl(initialAccount.levelOfForm, { nonNullable: true }),
    activated: new FormControl(initialAccount.activated, { nonNullable: true }),
    editsLeft: new FormControl(initialAccount.editsLeft, { nonNullable: true }),
  });

  constructor(
    private accountService: AccountService,
    private translateService: TranslateService,
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.settingsForm.patchValue(account);
        this.isTrueCoffin = true;
        console.log(this.isTrueCoffin);
        console.log(initialAccount);
      }
    });
  }

  onClick(): void {
    console.log(initialAccount);
    console.log(this.accountService.userIdentity);
    this.http
      .post(this.applicationConfigService.getEndpointFor('/api/profile/get/data'), '!TVzk')
      // eslint-disable-next-line no-console
      .subscribe(res => console.log(res));
  }

  save(): void {
    this.success = false;

    const account = this.settingsForm.getRawValue();
    this.accountService.save(account).subscribe(
      res => {
        if (res == 'FORBIDDEN') {
          console.log('chujjj');
        } else {
          this.success = true;

          this.accountService.authenticate(account);

          if (account.langKey !== this.translateService.currentLang) {
            this.translateService.use(account.langKey);
          }
        }
      },
      error => {
        console.log(error);
      }
    );
  }
}
