import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { Register, registeration } from './register.model';

@Injectable({ providedIn: 'root' })
export class RegisterService {
  success = false;

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  save(register: Register): Observable<{}> {
    return this.http.post(this.applicationConfigService.getEndpointFor('api/register'), register);
  }

  // changeRegistration(registration: registeration): registeration1 {
  //   return new registeration1(
  //     registration.reg1.login,
  //     registration.reg1.email,
  //     registration.reg1.password,
  //     registration.reg1.langKey,
  //     registration.reg2.firstName,
  //     registration.reg2.lastName,
  //     registration.reg2.prefix,
  //     registration.reg2.phoneNumber,
  //     registration.reg3.burialMethod,
  //     registration.reg3.clothes,
  //     registration.reg3.placeOfCeremony,
  //     registration.reg3.photo,
  //     registration.reg3.graveInscription,
  //     registration.reg3.spotify,
  //     registration.reg3.guests,
  //     registration.reg3.notInvited,
  //     registration.reg3.obituary,
  //     registration.reg4.purchasedPlace,
  //     registration.reg4.isPurchasedOther,
  //     registration.reg4.flowers,
  //     registration.reg4.ifFlowers,
  //     registration.reg4.farwellLetter,
  //     registration.reg4.speech,
  //     registration.reg4.videoSpeech,
  //     registration.reg4.testament,
  //     registration.reg4.accessForRelatives,
  //     registration.reg4.other
  //   );
  // }
}
