import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProfileService } from '../service/profile.service';

import { ProfileComponent } from './profile.component';

describe('Profile Management Component', () => {
  let comp: ProfileComponent;
  let fixture: ComponentFixture<ProfileComponent>;
  let service: ProfileService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'profile', component: ProfileComponent }]), HttpClientTestingModule],
      declarations: [ProfileComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(ProfileComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProfileComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ProfileService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.profiles?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to profileService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getProfileIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getProfileIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
