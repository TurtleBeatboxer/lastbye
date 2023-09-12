import { Injectable } from '@angular/core';
import { Account } from 'app/core/auth/account.model';
import { Observable, Subject } from 'rxjs';
import { profileFormData1, profileFormData2, profileFormData3, profileFormData4 } from '../profile-form/profile-form.model';

export const EventTypes = {
  EventType: 'edit',
};

interface edit {
  data: data2 | data2;
}
interface data2 {
  burialMethod: string;
  burialPlace: string;
  graveInscription: string;
  openCoffin: boolean;
  clothes: string;
}

interface data1 {
  name: string;
  surname: string;
  prefix: number;
  phone: number;
}

@Injectable({
  providedIn: 'root',
})
export class EditEventServiceService {
  constructor() {}
  private myAppEventSubject: Subject<any> = new Subject<any>();

  public readonly myAppEvent$: Observable<any> = this.myAppEventSubject.asObservable();
  broadcast(data: any) {
    this.myAppEventSubject.next({
      data: data,
    });
  }
}
