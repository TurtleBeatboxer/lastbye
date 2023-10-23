import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProfileFormService } from 'app/account/profile-form/profile-form.service';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { EditEventServiceService } from '../edit-event-service.service';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-my-funeral-edit',
  templateUrl: './my-funeral-edit.component.html',
  styleUrls: ['../../profile-form/profile-form.component.scss'],
})
export class MyFuneralEditComponent implements OnInit {
  profileForm3 = this.profileFormService.buildForm3();
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
      this.profileForm3.patchValue(value);
    });
    this.accountService.selectedData$.subscribe(value => {
      this.id = value?.id;
    });
  }

  submit() {
    this.http
      .patch(this.applicationService.getEndpointFor(`/api/profiles/${this.id}`), { ...this.profileForm3.getRawValue(), id: this.id })
      .subscribe((res: Account) => {
        this.backToEdit();
        this.accountService.patchNewAccountValue(res);
      });
  }

  backToEdit() {
    this.router.navigate(['/user/profile']);
  }
}
