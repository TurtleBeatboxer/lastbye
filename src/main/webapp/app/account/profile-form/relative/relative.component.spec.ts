import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RelativeComponent } from './relative.component';

describe('RelativeComponent', () => {
  let component: RelativeComponent;
  let fixture: ComponentFixture<RelativeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RelativeComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(RelativeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
