import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'jhi-profile-form-picture',
  templateUrl: './profile-form-picture.component.html',
  styleUrls: ['./profile-form-picture.component.scss'],
})
export class ProfileFormPictureComponent {
  constructor(private router: Router) {}
  redirect() {
    this.router.navigate(['user/tell-us'], { skipLocationChange: true });
  }
  save() {
    this.redirect();
  }
}
