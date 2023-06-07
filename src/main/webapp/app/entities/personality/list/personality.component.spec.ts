import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PersonalityService } from '../service/personality.service';

import { PersonalityComponent } from './personality.component';

describe('Personality Management Component', () => {
  let comp: PersonalityComponent;
  let fixture: ComponentFixture<PersonalityComponent>;
  let service: PersonalityService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'personality', component: PersonalityComponent }]), HttpClientTestingModule],
      declarations: [PersonalityComponent],
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
      .overrideTemplate(PersonalityComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PersonalityComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PersonalityService);

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
    expect(comp.personalities?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to personalityService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPersonalityIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPersonalityIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
