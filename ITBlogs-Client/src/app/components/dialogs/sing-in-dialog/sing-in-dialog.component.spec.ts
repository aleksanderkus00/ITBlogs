import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SingInDialogComponent } from './sing-in-dialog.component';

describe('SingInDialogComponent', () => {
  let component: SingInDialogComponent;
  let fixture: ComponentFixture<SingInDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SingInDialogComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SingInDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
