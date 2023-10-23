import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ApplicationConfig } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { AccountService } from 'app/core/auth/account.service';
import { ApplicationConfigService } from 'app/core/config/application-config.service';

@Component({
  selector: 'jhi-tell-us',
  templateUrl: './tell-us.component.html',
  styleUrls: ['./tell-us.component.scss'],
})
export class TellUsComponent {
  constructor(
    private router: Router,
    private accountService: AccountService,
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  redirect() {
    if (this.accountService.userIdentity) {
      this.accountService.userIdentity.levelOfForm = 1;
      this.router.navigate(['user/profile1']);
    }
  }

  getPicture() {
    this.http.get(this.applicationConfigService.getEndpointFor('api/user/pictures')).subscribe(res => {
      console.log(res);
    });
  }
}
