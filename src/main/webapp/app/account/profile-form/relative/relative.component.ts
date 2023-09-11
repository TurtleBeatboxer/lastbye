import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { Relative } from '../profile-form.model';

@Component({
  selector: 'jhi-relative',
  templateUrl: './relative.component.html',
  styleUrls: ['./relative.component.scss'],
})
export class RelativeComponent implements OnInit {
  @Input() data: Relative;
  @Input() index: number;
  @Output() deleteItemEvent = new EventEmitter();
  constructor() {}

  deleteRelative(): void {
    this.deleteItemEvent.emit(this.index);
    console.log('click');
  }

  ngOnInit(): void {}
}
