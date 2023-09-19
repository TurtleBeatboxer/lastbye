import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BasicInfoEditComponent } from './basic-info-edit.component';

describe('BasicInfoEditComponent', () => {
  let component: BasicInfoEditComponent;
  let fixture: ComponentFixture<BasicInfoEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BasicInfoEditComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BasicInfoEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
