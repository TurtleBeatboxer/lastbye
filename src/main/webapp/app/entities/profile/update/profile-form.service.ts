import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProfile, NewProfile } from '../profile.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProfile for edit and NewProfileFormGroupInput for create.
 */
type ProfileFormGroupInput = IProfile | PartialWithRequiredKeyOf<NewProfile>;

type ProfileFormDefaults = Pick<NewProfile, 'id' | 'purchasedPlace' | 'flowers'>;

type ProfileFormGroupContent = {
  id: FormControl<IProfile['id'] | NewProfile['id']>;
  surname: FormControl<IProfile['surname']>;
  phone: FormControl<IProfile['phone']>;
  prefix: FormControl<IProfile['prefix']>;
  burialMethod: FormControl<IProfile['burialMethod']>;
  clothes: FormControl<IProfile['clothes']>;
  placeOfCeremony: FormControl<IProfile['placeOfCeremony']>;
  photo: FormControl<IProfile['photo']>;
  graveInscription: FormControl<IProfile['graveInscription']>;
  spotify: FormControl<IProfile['spotify']>;
  guests: FormControl<IProfile['guests']>;
  notInvited: FormControl<IProfile['notInvited']>;
  obituary: FormControl<IProfile['obituary']>;
  purchasedPlace: FormControl<IProfile['purchasedPlace']>;
  ifPurchasedOther: FormControl<IProfile['ifPurchasedOther']>;
  flowers: FormControl<IProfile['flowers']>;
  ifFlowers: FormControl<IProfile['ifFlowers']>;
  farewellLetter: FormControl<IProfile['farewellLetter']>;
  speech: FormControl<IProfile['speech']>;
  videoSpeech: FormControl<IProfile['videoSpeech']>;
  testament: FormControl<IProfile['testament']>;
  accessesForRelatives: FormControl<IProfile['accessesForRelatives']>;
  other: FormControl<IProfile['other']>;
  codeQR: FormControl<IProfile['codeQR']>;
  user: FormControl<IProfile['user']>;
};

export type ProfileFormGroup = FormGroup<ProfileFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProfileFormService {
  createProfileFormGroup(profile: ProfileFormGroupInput = { id: null }): ProfileFormGroup {
    const profileRawValue = {
      ...this.getFormDefaults(),
      ...profile,
    };
    return new FormGroup<ProfileFormGroupContent>({
      id: new FormControl(
        { value: profileRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      surname: new FormControl(profileRawValue.surname),
      phone: new FormControl(profileRawValue.phone),
      prefix: new FormControl(profileRawValue.prefix),
      burialMethod: new FormControl(profileRawValue.burialMethod),
      clothes: new FormControl(profileRawValue.clothes),
      placeOfCeremony: new FormControl(profileRawValue.placeOfCeremony),
      photo: new FormControl(profileRawValue.photo),
      graveInscription: new FormControl(profileRawValue.graveInscription),
      spotify: new FormControl(profileRawValue.spotify),
      guests: new FormControl(profileRawValue.guests),
      notInvited: new FormControl(profileRawValue.notInvited),
      obituary: new FormControl(profileRawValue.obituary),
      purchasedPlace: new FormControl(profileRawValue.purchasedPlace),
      ifPurchasedOther: new FormControl(profileRawValue.ifPurchasedOther),
      flowers: new FormControl(profileRawValue.flowers),
      ifFlowers: new FormControl(profileRawValue.ifFlowers),
      farewellLetter: new FormControl(profileRawValue.farewellLetter),
      speech: new FormControl(profileRawValue.speech),
      videoSpeech: new FormControl(profileRawValue.videoSpeech),
      testament: new FormControl(profileRawValue.testament),
      accessesForRelatives: new FormControl(profileRawValue.accessesForRelatives),
      other: new FormControl(profileRawValue.other),
      codeQR: new FormControl(profileRawValue.codeQR),
      user: new FormControl(profileRawValue.user),
    });
  }

  getProfile(form: ProfileFormGroup): IProfile | NewProfile {
    return form.getRawValue() as IProfile | NewProfile;
  }

  resetForm(form: ProfileFormGroup, profile: ProfileFormGroupInput): void {
    const profileRawValue = { ...this.getFormDefaults(), ...profile };
    form.reset(
      {
        ...profileRawValue,
        id: { value: profileRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProfileFormDefaults {
    return {
      id: null,
      purchasedPlace: false,
      flowers: false,
    };
  }
}
