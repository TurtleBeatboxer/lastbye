import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../personality.test-samples';

import { PersonalityFormService } from './personality-form.service';

describe('Personality Form Service', () => {
  let service: PersonalityFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PersonalityFormService);
  });

  describe('Service methods', () => {
    describe('createPersonalityFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPersonalityFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            relativeEmail: expect.any(Object),
            profile: expect.any(Object),
          })
        );
      });

      it('passing IPersonality should create a new form with FormGroup', () => {
        const formGroup = service.createPersonalityFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            relativeEmail: expect.any(Object),
            profile: expect.any(Object),
          })
        );
      });
    });

    describe('getPersonality', () => {
      it('should return NewPersonality for default Personality initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPersonalityFormGroup(sampleWithNewData);

        const personality = service.getPersonality(formGroup) as any;

        expect(personality).toMatchObject(sampleWithNewData);
      });

      it('should return NewPersonality for empty Personality initial value', () => {
        const formGroup = service.createPersonalityFormGroup();

        const personality = service.getPersonality(formGroup) as any;

        expect(personality).toMatchObject({});
      });

      it('should return IPersonality', () => {
        const formGroup = service.createPersonalityFormGroup(sampleWithRequiredData);

        const personality = service.getPersonality(formGroup) as any;

        expect(personality).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPersonality should not enable id FormControl', () => {
        const formGroup = service.createPersonalityFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPersonality should disable id FormControl', () => {
        const formGroup = service.createPersonalityFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
