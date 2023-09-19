import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProfileFormService } from 'app/account/profile-form/profile-form.service';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { EditEventServiceService } from '../edit-event-service.service';

@Component({
  selector: 'jhi-my-funeral-edit',
  templateUrl: './my-funeral-edit.component.html',
  styleUrls: ['../../profile-form/profile-form.component.scss'],
})
export class MyFuneralEditComponent implements OnInit {
  profileForm3 = this.profileFormService.buildForm3();
  constructor(
    private profileFormService: ProfileFormService,
    private router: Router,
    private http: HttpClient,
    private applicationService: ApplicationConfigService,
    private messageService: EditEventServiceService
  ) {}

  ngOnInit(): void {
    this.messageService.selectedData$.subscribe(value => {
      this.profileForm3.patchValue(value);
      console.log(value);
    });
  }

  submit() {
    this.http
      .post(this.applicationService.getEndpointFor('/api/account'), { ...this.profileForm3.getRawValue(), login: 1234 })
      .subscribe(res => {
        console.log(res);
      });
  }

  backToEdit() {
    this.router.navigate(['/user/profile']);
  }
}
