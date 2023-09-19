import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyLastMessageEditComponent } from './my-last-message-edit.component';

describe('MyLastMessageEditComponent', () => {
  let component: MyLastMessageEditComponent;
  let fixture: ComponentFixture<MyLastMessageEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MyLastMessageEditComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(MyLastMessageEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
