import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyBurialEditComponent } from './my-burial-edit.component';

describe('MyBurialEditComponent', () => {
  let component: MyBurialEditComponent;
  let fixture: ComponentFixture<MyBurialEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MyBurialEditComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(MyBurialEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
