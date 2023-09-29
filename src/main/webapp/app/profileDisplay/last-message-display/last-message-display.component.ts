import { Component, Input, OnInit } from '@angular/core';
import { Account } from 'app/core/auth/account.model';

@Component({
  selector: 'jhi-last-message-display',
  templateUrl: './last-message-display.component.html',
  styleUrls: ['./last-message-display.component.css'],
})
export class LastMessageDisplayComponent implements OnInit {
  @Input() account: Account;
  constructor() {}

  ngOnInit(): void {}
}
