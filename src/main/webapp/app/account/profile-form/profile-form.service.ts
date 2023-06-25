import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { profileData2, profileData3, profileData4, profileForm2, profileForm3, profileForm4 } from './profile-form.model';
import { AccountService } from 'app/core/auth/account.service';

@Injectable({
  providedIn: 'root',
})
export class ProfileFormService {
  constructor(private accountService: AccountService) {}

  dataProfile2(form: profileForm2): profileData2 {
    if (this.accountService.userIdentity) {
      return new profileData2(
        form.burialMethod,
        form.graveInscription,
        form.openCoffin,
        form.clothes,
        this.accountService.userIdentity.userId,
        this.accountService.userIdentity.login,
        1
      );
    } else {
      return new profileData2('', '', false, '', -1, '', 0);
    }
  }

  dataProfile3(form: profileForm3): profileData3 {
    if (this.accountService.userIdentity) {
      return new profileData3(
        form.flowers,
        form.ifFlowers,
        form.obituary,
        form.spotify,
        form.guests,
        form.notInvited,
        form.placeOfCeremony,
        this.accountService.userIdentity.userId,
        this.accountService.userIdentity.login,
        2
      );
    } else {
      return new profileData3(false, '', '', '', '', '', '', -1, '', 0);
    }
  }

  dataProfile4(form: profileForm4): profileData4 {
    if (this.accountService.userIdentity) {
      return new profileData4(
        form.farewellLetter,
        form.videoSpeech,
        form.testament,
        form.other,
        this.accountService.userIdentity.userId,
        this.accountService.userIdentity.login,
        3
      );
    } else {
      return new profileData4('', '', '', '', -1, '', 0);
    }
  }
}
