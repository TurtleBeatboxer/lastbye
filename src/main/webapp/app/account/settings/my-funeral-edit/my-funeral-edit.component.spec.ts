import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyFuneralEditComponent } from './my-funeral-edit.component';

describe('MyFuneralEditComponent', () => {
  let component: MyFuneralEditComponent;
  let fixture: ComponentFixture<MyFuneralEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MyFuneralEditComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(MyFuneralEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
