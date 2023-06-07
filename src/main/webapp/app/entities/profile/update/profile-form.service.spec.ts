import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../profile.test-samples';

import { ProfileFormService } from './profile-form.service';

describe('Profile Form Service', () => {
  let service: ProfileFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProfileFormService);
  });

  describe('Service methods', () => {
    describe('createProfileFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProfileFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            surname: expect.any(Object),
            phone: expect.any(Object),
            prefix: expect.any(Object),
            burialMethod: expect.any(Object),
            clothes: expect.any(Object),
            placeOfCeremony: expect.any(Object),
            photo: expect.any(Object),
            graveInscription: expect.any(Object),
            spotify: expect.any(Object),
            guests: expect.any(Object),
            notInvited: expect.any(Object),
            obituary: expect.any(Object),
            purchasedPlace: expect.any(Object),
            ifPurchasedOther: expect.any(Object),
            flowers: expect.any(Object),
            ifFlowers: expect.any(Object),
            farewellLetter: expect.any(Object),
            speech: expect.any(Object),
            videoSpeech: expect.any(Object),
            testament: expect.any(Object),
            accessesForRelatives: expect.any(Object),
            other: expect.any(Object),
            codeQR: expect.any(Object),
            user: expect.any(Object),
          })
        );
      });

      it('passing IProfile should create a new form with FormGroup', () => {
        const formGroup = service.createProfileFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            surname: expect.any(Object),
            phone: expect.any(Object),
            prefix: expect.any(Object),
            burialMethod: expect.any(Object),
            clothes: expect.any(Object),
            placeOfCeremony: expect.any(Object),
            photo: expect.any(Object),
            graveInscription: expect.any(Object),
            spotify: expect.any(Object),
            guests: expect.any(Object),
            notInvited: expect.any(Object),
            obituary: expect.any(Object),
            purchasedPlace: expect.any(Object),
            ifPurchasedOther: expect.any(Object),
            flowers: expect.any(Object),
            ifFlowers: expect.any(Object),
            farewellLetter: expect.any(Object),
            speech: expect.any(Object),
            videoSpeech: expect.any(Object),
            testament: expect.any(Object),
            accessesForRelatives: expect.any(Object),
            other: expect.any(Object),
            codeQR: expect.any(Object),
            user: expect.any(Object),
          })
        );
      });
    });

    describe('getProfile', () => {
      it('should return NewProfile for default Profile initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProfileFormGroup(sampleWithNewData);

        const profile = service.getProfile(formGroup) as any;

        expect(profile).toMatchObject(sampleWithNewData);
      });

      it('should return NewProfile for empty Profile initial value', () => {
        const formGroup = service.createProfileFormGroup();

        const profile = service.getProfile(formGroup) as any;

        expect(profile).toMatchObject({});
      });

      it('should return IProfile', () => {
        const formGroup = service.createProfileFormGroup(sampleWithRequiredData);

        const profile = service.getProfile(formGroup) as any;

        expect(profile).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProfile should not enable id FormControl', () => {
        const formGroup = service.createProfileFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProfile should disable id FormControl', () => {
        const formGroup = service.createProfileFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
