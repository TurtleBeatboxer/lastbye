import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SecurityEditComponent } from './security-edit.component';

describe('SecurityEditComponent', () => {
  let component: SecurityEditComponent;
  let fixture: ComponentFixture<SecurityEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SecurityEditComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SecurityEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
