import { Injectable } from '@angular/core';
import { profileFormData2, profileFormData3, profileFormData4 } from './profile-form.model';
import { AccountService } from 'app/core/auth/account.service';

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
