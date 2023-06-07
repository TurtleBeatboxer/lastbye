import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonality } from '../personality.model';

@Component({
  selector: 'jhi-personality-detail',
  templateUrl: './personality-detail.component.html',
})
export class PersonalityDetailComponent implements OnInit {
  personality: IPersonality | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personality }) => {
      this.personality = personality;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
