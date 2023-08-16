import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CodeQrComponent } from './code-qr.component';

describe('CodeQrComponent', () => {
  let component: CodeQrComponent;
  let fixture: ComponentFixture<CodeQrComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CodeQrComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CodeQrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
