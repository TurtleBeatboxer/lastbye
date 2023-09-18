import { Injectable } from '@angular/core';
import { Account } from 'app/core/auth/account.model';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { profileFormData1, profileFormData2, profileFormData3, profileFormData4 } from '../profile-form/profile-form.model';

@Injectable({
  providedIn: 'root',
})
export class EditEventServiceService {
  private data$ = new BehaviorSubject({});
  selectedData$ = this.data$.asObservable();
  constructor() {}

  setData(data: any) {
    this.data$.next(data);
  }
}
