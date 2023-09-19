import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-tell-us',
  templateUrl: './tell-us.component.html',
  styleUrls: ['./tell-us.component.scss'],
})
export class TellUsComponent {
  constructor(private router: Router, private accountService: AccountService) {}

  redirect() {
    if (this.accountService.userIdentity) {
      this.accountService.userIdentity.levelOfForm = 1;
      this.router.navigate(['user/profile1']);
    }
  }
}
