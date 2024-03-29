import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProfileFormService } from 'app/account/profile-form/profile-form.service';
import { Account } from 'app/core/auth/account.model';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { EditEventServiceService } from '../edit-event-service.service';

@Component({
  selector: 'jhi-my-burial-edit',
  templateUrl: './my-burial-edit.component.html',
  styleUrls: ['../../profile-form/profile-form.component.scss'],
})
export class MyBurialEditComponent implements OnInit {
  @Input() eventEmitter: EventEmitter<Account>;
  burialType;
  otherCremationInput;
  urn;
  otherCremation = false;
  profileForm2 = this.profileFormService.buildForm2();

  constructor(
    private profileFormService: ProfileFormService,
    private router: Router,
    private http: HttpClient,
    private applicationService: ApplicationConfigService,
    private messageService: EditEventServiceService
  ) {}

  ngOnInit(): void {
    this.messageService.selectedData$.subscribe((value: Account) => {
      this.profileForm2.patchValue(value);
      if (
        value.burialType === 'cremation' &&
        value.burialMethod !== 'urn' &&
        value.burialMethod !== 'jewellery' &&
        value.burialMethod !== 'sea' &&
        value.burialMethod !== 'vinyl'
      ) {
        this.onOtherCremationClickFirst();
      }
      this.burialType = value.burialType;
      console.log(value);
    });
  }

  submit() {
    this.http
      .post(this.applicationService.getEndpointFor('/api/account'), { ...this.profileForm2.getRawValue(), login: 1234 })
      .subscribe(res => {
        console.log({ ...this.profileForm2.getRawValue(), login: 1234 });
      });
  }

  backToEdit() {
    this.router.navigate(['/user/profile']);
  }

  onCoffinClick(): void {
    this.profileForm2.patchValue({ burialType: 'coffin' });
    this.otherCremation = false;
    this.otherCremationInput = '';
    this.profileForm2.reset();
    this.profileForm2.patchValue({ burialType: 'coffin' });
  }

  onOtherClicked(): void {
    this.profileForm2.patchValue({ burialType: '' });
    this.otherCremation = false;
    this.otherCremationInput = '';
    this.profileForm2.reset();
  }

  onCremationClick(): void {
    this.otherCremation = false;
    this.otherCremationInput = '';
    this.profileForm2.reset();
    this.profileForm2.patchValue({ burialType: 'cremation' });
  }

  otherCremationFalse(): void {
    this.otherCremation = false;

    this.otherCremationInput = '';
  }

  onOtherCremationClick(): void {
    this.profileForm2.patchValue({ burialMethod: '' });
  }

  onOtherCremationClickFirst() {
    this.otherCremationInput = 'cremation-other';
  }
}
