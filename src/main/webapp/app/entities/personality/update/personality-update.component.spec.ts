import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PersonalityFormService } from './personality-form.service';
import { PersonalityService } from '../service/personality.service';
import { IPersonality } from '../personality.model';
import { IProfile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';

import { PersonalityUpdateComponent } from './personality-update.component';

describe('Personality Management Update Component', () => {
  let comp: PersonalityUpdateComponent;
  let fixture: ComponentFixture<PersonalityUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let personalityFormService: PersonalityFormService;
  let personalityService: PersonalityService;
  let profileService: ProfileService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PersonalityUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PersonalityUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PersonalityUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    personalityFormService = TestBed.inject(PersonalityFormService);
    personalityService = TestBed.inject(PersonalityService);
    profileService = TestBed.inject(ProfileService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Profile query and add missing value', () => {
      const personality: IPersonality = { id: 456 };
      const profile: IProfile = { id: 86820 };
      personality.profile = profile;

      const profileCollection: IProfile[] = [{ id: 82424 }];
      jest.spyOn(profileService, 'query').mockReturnValue(of(new HttpResponse({ body: profileCollection })));
      const additionalProfiles = [profile];
      const expectedCollection: IProfile[] = [...additionalProfiles, ...profileCollection];
      jest.spyOn(profileService, 'addProfileToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ personality });
      comp.ngOnInit();

      expect(profileService.query).toHaveBeenCalled();
      expect(profileService.addProfileToCollectionIfMissing).toHaveBeenCalledWith(
        profileCollection,
        ...additionalProfiles.map(expect.objectContaining)
      );
      expect(comp.profilesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const personality: IPersonality = { id: 456 };
      const profile: IProfile = { id: 8216 };
      personality.profile = profile;

      activatedRoute.data = of({ personality });
      comp.ngOnInit();

      expect(comp.profilesSharedCollection).toContain(profile);
      expect(comp.personality).toEqual(personality);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonality>>();
      const personality = { id: 123 };
      jest.spyOn(personalityFormService, 'getPersonality').mockReturnValue(personality);
      jest.spyOn(personalityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personality });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personality }));
      saveSubject.complete();

      // THEN
      expect(personalityFormService.getPersonality).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(personalityService.update).toHaveBeenCalledWith(expect.objectContaining(personality));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonality>>();
      const personality = { id: 123 };
      jest.spyOn(personalityFormService, 'getPersonality').mockReturnValue({ id: null });
      jest.spyOn(personalityService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personality: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personality }));
      saveSubject.complete();

      // THEN
      expect(personalityFormService.getPersonality).toHaveBeenCalled();
      expect(personalityService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonality>>();
      const personality = { id: 123 };
      jest.spyOn(personalityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personality });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(personalityService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProfile', () => {
      it('Should forward to profileService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(profileService, 'compareProfile');
        comp.compareProfile(entity, entity2);
        expect(profileService.compareProfile).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
