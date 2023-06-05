import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { LoginModel } from 'src/app/models/login.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-sing-in-dialog',
  templateUrl: './sing-in-dialog.component.html',
  styleUrls: ['./sing-in-dialog.component.scss'],
})
export class SingInDialogComponent {
  constructor(
    private userService: UserService,
    public dialogRef: MatDialogRef<SingInDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: LoginModel
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}
