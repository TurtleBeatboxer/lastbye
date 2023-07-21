import { Injectable } from '@angular/core';
import { profileFormData1, profileFormData2, profileFormData3, profileFormData4 } from './profile-form.model';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

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

  // patchProfile1(user: Account): profileFormData1 {
  //   if (user.firstName && user.lastName) {
  //     return { firstName: user.firstName, lastName: user.lastName, prefix: user.prefix, phone: user.phone };
  //   }
  //   return { firstName: '', lastName: '', prefix: '', phone: 0 };
  // }

  // patchProfile2(user: Account): profileFormData2 {
  //   return {
  //     burialMethod: user.burialMethod,
  //     graveInscription: user.graveInscription,
  //     openCoffin: user.openCoffin,
  //     clothes: user.clothes,
  //   };
  // }
  // patchProfile3(user: Account): profileFormData3 {
  //   return {
  //     flowers: user.flowers,
  //     ifFlowers: user.ifFlowers,
  //     obituary: user.obituary,
  //     spotify: user.spotify,
  //     guests: user.guests,
  //     notInvited: user.notInvited,
  //     placeOfCeremony: user.placeOfCeremony,
  //   };
  // }
  // patchProfile4(user: Account): profileFormData4 {
  //   return {
  //     farewellLetter: user.farewellLetter,
  //     videoSpeech: user.videoSpeech,
  //     testament: user.testament,
  //     other: user.other,
  //   };
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
