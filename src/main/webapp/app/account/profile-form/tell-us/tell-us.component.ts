import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-tell-us',
  templateUrl: './tell-us.component.html',
  styleUrls: ['./tell-us.component.scss'],
})
export class TellUsComponent {
  constructor(private router: Router) {}

  redirect() {
    this.router.navigate(['user/profile1']);
  }
}
