import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPersonality, NewPersonality } from '../personality.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPersonality for edit and NewPersonalityFormGroupInput for create.
 */
type PersonalityFormGroupInput = IPersonality | PartialWithRequiredKeyOf<NewPersonality>;

type PersonalityFormDefaults = Pick<NewPersonality, 'id'>;

type PersonalityFormGroupContent = {
  id: FormControl<IPersonality['id'] | NewPersonality['id']>;
  relativeEmail: FormControl<IPersonality['relativeEmail']>;
  profile: FormControl<IPersonality['profile']>;
};

export type PersonalityFormGroup = FormGroup<PersonalityFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PersonalityFormService {
  createPersonalityFormGroup(personality: PersonalityFormGroupInput = { id: null }): PersonalityFormGroup {
    const personalityRawValue = {
      ...this.getFormDefaults(),
      ...personality,
    };
    return new FormGroup<PersonalityFormGroupContent>({
      id: new FormControl(
        { value: personalityRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      relativeEmail: new FormControl(personalityRawValue.relativeEmail),
      profile: new FormControl(personalityRawValue.profile),
    });
  }

  getPersonality(form: PersonalityFormGroup): IPersonality | NewPersonality {
    return form.getRawValue() as IPersonality | NewPersonality;
  }

  resetForm(form: PersonalityFormGroup, personality: PersonalityFormGroupInput): void {
    const personalityRawValue = { ...this.getFormDefaults(), ...personality };
    form.reset(
      {
        ...personalityRawValue,
        id: { value: personalityRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PersonalityFormDefaults {
    return {
      id: null,
    };
  }
}
