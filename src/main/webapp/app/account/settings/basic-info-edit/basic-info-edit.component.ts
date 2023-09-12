import { Component, OnInit } from '@angular/core';
import { ProfileFormService } from 'app/account/profile-form/profile-form.service';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { EditEventServiceService } from '../edit-event-service.service';
@Component({
  selector: 'jhi-basic-info-edit',
  templateUrl: './basic-info-edit.component.html',
  styleUrls: ['../../profile-form/profile-form.component.scss', './basic-info-edit.component.scss'],
})
export class BasicInfoEditComponent implements OnInit {
  profileForm1 = this.profileFormService.buildForm1();
  constructor(
    private profileFormService: ProfileFormService,
    private router: Router,
    private http: HttpClient,
    private applicationService: ApplicationConfigService,
    private messageService: EditEventServiceService
  ) {}

  ngOnInit(): void {
    this.messageService.myAppEvent$.subscribe(ev => {
      console.log(ev.data);
      this.profileForm1.patchValue(ev.data);
    });
  }

  submit() {
    this.http
      .post(this.applicationService.getEndpointFor('/api/account'), { ...this.profileForm1.getRawValue(), login: 1234 })
      .subscribe(res => {
        console.log(res);
      });
    this.backToEdit();
  }

  backToEdit() {
    this.router.navigate(['/user/profile']);
  }
}
