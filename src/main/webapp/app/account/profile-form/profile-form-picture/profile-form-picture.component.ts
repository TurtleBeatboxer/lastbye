import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from 'app/core/auth/account.service';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
@Component({
  selector: 'jhi-profile-form-picture',
  templateUrl: './profile-form-picture.component.html',
  styleUrls: ['./profile-form-picture.component.scss'],
})
export class ProfileFormPictureComponent implements OnInit {
  url: any;
  file: File;
  constructor(
    private router: Router,
    private applicationConfigService: ApplicationConfigService,
    private accountService: AccountService,
    private http: HttpClient
  ) {}
  redirect() {
    this.router.navigate(['user/tell-us'], { skipLocationChange: true });
  }

  ngOnInit(): void {
    console.log(this.accountService.userIdentity?.userId);
  }

  save() {
    if (this.file) {
      const formData = new FormData();

      formData.append('file', this.file);
      formData.append('type', 'formProfilePicture');
      if (this.accountService.userIdentity) {
        formData.append('user', this.accountService.userIdentity.userId.toString());
      }
      const upload$ = this.http.post(this.applicationConfigService.getEndpointFor('/api/profile/pictures'), formData);

      upload$.subscribe(res => {
        console.log(res);
      });
      this.redirect();
    }
  }

  onFileSelected(event) {
    this.file = event.target.files[0];

    const reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);

    reader.onload = _event => {
      this.url = reader.result;
    };
  }
}
