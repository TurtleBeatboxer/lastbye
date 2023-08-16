import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TellUsComponent } from './tell-us.component';

describe('TellUsComponent', () => {
  let component: TellUsComponent;
  let fixture: ComponentFixture<TellUsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TellUsComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(TellUsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
