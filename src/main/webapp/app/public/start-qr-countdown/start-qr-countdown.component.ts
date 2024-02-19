import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'jhi-start-qr-countdown',
  templateUrl: './start-qr-countdown.component.html',
  styleUrls: ['./start-qr-countdown.component.css'],
})
export class StartQrCountdownComponent implements OnInit {
  question: string;
  startQr = new FormGroup({
    answer: new FormControl('', { nonNullable: true }),
    email: new FormControl('', { nonNullable: true }),
  });
  constructor() {}

  ngOnInit(): void {}
  submit() {}
}
