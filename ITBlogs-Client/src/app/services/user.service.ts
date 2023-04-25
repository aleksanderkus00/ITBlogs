import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { RegisterModel } from '../models/register.moder';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { LoginModel } from '../models/login.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private env = environment;
  public user: boolean;

  constructor(private http: HttpClient) {
    this.user = true;
  }

  public signUp(registerModel: RegisterModel): Observable<boolean> {
    const data = JSON.stringify({
      username: registerModel.username,
      email: registerModel.email,
      password: registerModel.password,
    });
    const options = { headers: { 'Content-Type': 'application/json' } };
    return this.http
      .post<boolean>(`${this.env.apiUrl}/user/register`, data, options)
      .pipe(tap(response => (this.user = response)));
  }

  public signIn(loginModel: LoginModel): Observable<boolean> {
    const data = JSON.stringify(loginModel);
    const options = { headers: { 'Content-Type': 'application/json' } };
    return this.http
      .post<boolean>(`${this.env.apiUrl}/user/login`, data, options)
      .pipe(tap(response => (this.user = response)));
  }
}
