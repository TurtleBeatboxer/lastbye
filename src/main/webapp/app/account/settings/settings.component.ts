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
    langKey: new FormControl(initialAccount.langKey, { nonNullable: true }),

    activated: new FormControl(initialAccount.activated, { nonNullable: true }),
    authorities: new FormControl(initialAccount.authorities, { nonNullable: true }),
    imageUrl: new FormControl(initialAccount.imageUrl, { nonNullable: true }),
    login: new FormControl(initialAccount.login, { nonNullable: true }),
    userId: new FormControl(initialAccount.userId, { nonNullable: true }),
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
      }
    });
  }

  onClick(): void {
    this.http
      .post(this.applicationConfigService.getEndpointFor('/api/profile/get/data'), '!TVzk')
      // eslint-disable-next-line no-console
      .subscribe(res => console.log(res));
  }

  save(): void {
    this.success = false;

    const account = this.settingsForm.getRawValue();
    this.accountService.save(account).subscribe(() => {
      this.success = true;

      this.accountService.authenticate(account);

      if (account.langKey !== this.translateService.currentLang) {
        this.translateService.use(account.langKey);
      }
    });
  }
}
