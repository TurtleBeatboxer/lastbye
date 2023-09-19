import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FilesFormService } from './files-form.service';
import { FilesService } from '../service/files.service';
import { IFiles } from '../files.model';
import { IProfile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';

import { FilesUpdateComponent } from './files-update.component';

describe('Files Management Update Component', () => {
  let comp: FilesUpdateComponent;
  let fixture: ComponentFixture<FilesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let filesFormService: FilesFormService;
  let filesService: FilesService;
  let profileService: ProfileService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FilesUpdateComponent],
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
      .overrideTemplate(FilesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FilesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    filesFormService = TestBed.inject(FilesFormService);
    filesService = TestBed.inject(FilesService);
    profileService = TestBed.inject(ProfileService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Profile query and add missing value', () => {
      const files: IFiles = { id: 456 };
      const profile: IProfile = { id: 68691 };
      files.profile = profile;

      const profileCollection: IProfile[] = [{ id: 90504 }];
      jest.spyOn(profileService, 'query').mockReturnValue(of(new HttpResponse({ body: profileCollection })));
      const additionalProfiles = [profile];
      const expectedCollection: IProfile[] = [...additionalProfiles, ...profileCollection];
      jest.spyOn(profileService, 'addProfileToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ files });
      comp.ngOnInit();

      expect(profileService.query).toHaveBeenCalled();
      expect(profileService.addProfileToCollectionIfMissing).toHaveBeenCalledWith(
        profileCollection,
        ...additionalProfiles.map(expect.objectContaining)
      );
      expect(comp.profilesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const files: IFiles = { id: 456 };
      const profile: IProfile = { id: 36323 };
      files.profile = profile;

      activatedRoute.data = of({ files });
      comp.ngOnInit();

      expect(comp.profilesSharedCollection).toContain(profile);
      expect(comp.files).toEqual(files);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFiles>>();
      const files = { id: 123 };
      jest.spyOn(filesFormService, 'getFiles').mockReturnValue(files);
      jest.spyOn(filesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ files });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: files }));
      saveSubject.complete();

      // THEN
      expect(filesFormService.getFiles).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(filesService.update).toHaveBeenCalledWith(expect.objectContaining(files));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFiles>>();
      const files = { id: 123 };
      jest.spyOn(filesFormService, 'getFiles').mockReturnValue({ id: null });
      jest.spyOn(filesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ files: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: files }));
      saveSubject.complete();

      // THEN
      expect(filesFormService.getFiles).toHaveBeenCalled();
      expect(filesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFiles>>();
      const files = { id: 123 };
      jest.spyOn(filesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ files });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(filesService.update).toHaveBeenCalled();
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
