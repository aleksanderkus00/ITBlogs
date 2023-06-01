import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { UserService } from 'src/app/services/user.service';
import { SingInDialogComponent } from '../dialogs/sing-in-dialog/sing-in-dialog.component';
import { SingUpDialogComponent } from '../dialogs/sing-up-dialog/sing-up-dialog.component';
import { LoginModel } from 'src/app/models/login.model';
import { RegisterModel } from 'src/app/models/register.moder';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent {
  loginCredentials: LoginModel | undefined;
  registerCredentials: RegisterModel | undefined;

  constructor(
    public userService: UserService,
    public dialog: MatDialog,
    private router: Router
  ) {}

  goHome() {
    this.router.navigate(['/']);
  }

  openDialogOnSignIn(): void {
    const dialogRef = this.dialog.open(SingInDialogComponent, {
      data: { email: '', password: '' },
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loginCredentials = result;
      // TODO: validate credentials
      if (typeof this.loginCredentials === 'undefined') return;
      this.userService
        .signIn(this.loginCredentials)
        .subscribe(response => console.log(response));
    });
  }

  openDialogOnSignUp(): void {
    const dialogRef = this.dialog.open(SingUpDialogComponent, {
      data: { username: '', email: '', password: '', repeatedPassword: '' },
    });

    dialogRef.afterClosed().subscribe(result => {
      this.registerCredentials = result;
      // TODO: validate credentials
      if (typeof this.registerCredentials === 'undefined') return;
      this.userService
        .signUp(this.registerCredentials)
        .subscribe(response => console.log(response));
    });
  }

  logout() {
    this.userService.logout();
  }
}
