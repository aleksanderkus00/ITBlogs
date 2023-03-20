import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { RegisterModel } from 'src/app/models/register.moder';

@Component({
  selector: 'app-sing-up-dialog',
  templateUrl: './sing-up-dialog.component.html',
  styleUrls: ['./sing-up-dialog.component.scss'],
})
export class SingUpDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<SingUpDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: RegisterModel
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}
