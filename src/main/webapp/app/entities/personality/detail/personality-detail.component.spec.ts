import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PersonalityDetailComponent } from './personality-detail.component';

describe('Personality Management Detail Component', () => {
  let comp: PersonalityDetailComponent;
  let fixture: ComponentFixture<PersonalityDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PersonalityDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ personality: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PersonalityDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PersonalityDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load personality on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.personality).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
