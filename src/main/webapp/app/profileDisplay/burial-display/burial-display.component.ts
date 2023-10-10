import { Component, Input, OnInit } from '@angular/core';
import { Account } from 'app/core/auth/account.model';

@Component({
  selector: 'jhi-burial-display',
  templateUrl: './burial-display.component.html',
  styleUrls: ['./burial-display.component.css', '../../account/settings/settings.component.scss'],
})
export class BurialDisplayComponent implements OnInit {
  @Input() account: Account;
  constructor() {}

  ngOnInit(): void {
    console.log(this.account);
  }
}
