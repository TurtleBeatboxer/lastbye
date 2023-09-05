import { Component, Input, OnInit } from '@angular/core';
import { Relative } from '../profile-form.model';

@Component({
  selector: 'jhi-relative',
  templateUrl: './relative.component.html',
  styleUrls: ['./relative.component.scss'],
})
export class RelativeComponent implements OnInit {
  @Input() data: Relative;
  constructor() {}

  ngOnInit(): void {}
}
