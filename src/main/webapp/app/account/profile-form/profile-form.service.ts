import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { profileData2, profileForm2 } from './profile-form.model';
import { AccountService } from 'app/core/auth/account.service';

@Injectable({
  providedIn: 'root',
})
export class ProfileFormService {
  constructor(private accountService: AccountService) {}

  dataProfile1(form: profileForm2): profileData2 {
    if (this.accountService.userIdentity) {
      return new profileData2(
        form.burialMethod,
        form.graveInscription,
        form.openCoffin,
        form.clothes,
        this.accountService.userIdentity.userId,
        this.accountService.userIdentity.login,
        2
      );
    } else {
      return new profileData2('', '', false, '', -1, '', 0);
    }
  }
}
