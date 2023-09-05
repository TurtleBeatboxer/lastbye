import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileFormPictureComponent } from './profile-form-picture.component';

describe('ProfileFormPictureComponent', () => {
  let component: ProfileFormPictureComponent;
  let fixture: ComponentFixture<ProfileFormPictureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfileFormPictureComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ProfileFormPictureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
