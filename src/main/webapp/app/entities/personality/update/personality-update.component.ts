import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PersonalityFormService, PersonalityFormGroup } from './personality-form.service';
import { IPersonality } from '../personality.model';
import { PersonalityService } from '../service/personality.service';
import { IProfile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';

@Component({
  selector: 'jhi-personality-update',
  templateUrl: './personality-update.component.html',
})
export class PersonalityUpdateComponent implements OnInit {
  isSaving = false;
  personality: IPersonality | null = null;

  profilesSharedCollection: IProfile[] = [];

  editForm: PersonalityFormGroup = this.personalityFormService.createPersonalityFormGroup();

  constructor(
    protected personalityService: PersonalityService,
    protected personalityFormService: PersonalityFormService,
    protected profileService: ProfileService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProfile = (o1: IProfile | null, o2: IProfile | null): boolean => this.profileService.compareProfile(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personality }) => {
      this.personality = personality;
      if (personality) {
        this.updateForm(personality);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personality = this.personalityFormService.getPersonality(this.editForm);
    if (personality.id !== null) {
      this.subscribeToSaveResponse(this.personalityService.update(personality));
    } else {
      this.subscribeToSaveResponse(this.personalityService.create(personality));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonality>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(personality: IPersonality): void {
    this.personality = personality;
    this.personalityFormService.resetForm(this.editForm, personality);

    this.profilesSharedCollection = this.profileService.addProfileToCollectionIfMissing<IProfile>(
      this.profilesSharedCollection,
      personality.profile
    );
  }

  protected loadRelationshipsOptions(): void {
    this.profileService
      .query()
      .pipe(map((res: HttpResponse<IProfile[]>) => res.body ?? []))
      .pipe(
        map((profiles: IProfile[]) => this.profileService.addProfileToCollectionIfMissing<IProfile>(profiles, this.personality?.profile))
      )
      .subscribe((profiles: IProfile[]) => (this.profilesSharedCollection = profiles));
  }
}
