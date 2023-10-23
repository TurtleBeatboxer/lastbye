import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProfileFormService } from 'app/account/profile-form/profile-form.service';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { EditEventServiceService } from '../edit-event-service.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

@Component({
  selector: 'jhi-my-last-message-edit',
  templateUrl: './my-last-message-edit.component.html',
  styleUrls: ['../../profile-form/profile-form.component.scss'],
})
export class MyLastMessageEditComponent implements OnInit {
  profileForm4 = this.profileFormService.buildForm4();
  id;
  constructor(
    private profileFormService: ProfileFormService,
    private router: Router,
    private http: HttpClient,
    private applicationService: ApplicationConfigService,
    private messageService: EditEventServiceService,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.messageService.selectedData$.subscribe(value => {
      this.profileForm4.patchValue(value);
    });
    this.accountService.selectedData$.subscribe(value => {
      this.id = value?.id;
    });
  }

  submit() {
    this.http
      .patch(this.applicationService.getEndpointFor(`/api/profiles/${this.id}`), { ...this.profileForm4.getRawValue(), id: this.id })
      .subscribe((res: Account) => {
        this.backToEdit();
        this.accountService.patchNewAccountValue(res);
      });
  }

  backToEdit() {
    this.router.navigate(['/user/profile']);
  }
}
