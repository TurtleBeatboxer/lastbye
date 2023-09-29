import { Component, Input, OnInit } from '@angular/core';
import { Account } from 'app/core/auth/account.model';

@Component({
  selector: 'jhi-funeral-display',
  templateUrl: './funeral-display.component.html',
  styleUrls: ['./funeral-display.component.css'],
})
export class FuneralDisplayComponent implements OnInit {
  @Input() account: Account;
  constructor() {}

  ngOnInit(): void {}
}
