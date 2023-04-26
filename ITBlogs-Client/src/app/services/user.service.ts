import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { RegisterModel } from '../models/register.moder';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { LoginModel } from '../models/login.model';
import { UserSettings } from '../models/user.settings';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private env = environment;
  private userId: number;
  public user: boolean;

  constructor(private http: HttpClient) {
    this.user = true;
    this.userId = 0;
  }

  public getUserId(): number {
    return this.userId;
  }

  public signUp(registerModel: RegisterModel): Observable<boolean> {
    const data = JSON.stringify({
      username: registerModel.username,
      email: registerModel.email,
      password: registerModel.password,
    });
    const options = { headers: { 'Content-Type': 'application/json' } };
    return this.http
      .post<number>(`${this.env.apiUrl}/user/register`, data, options)
      .pipe(
        map(response => {
          this.userId = response;
          this.user = !!response;
          return !!response;
        })
      );
  }

  public signIn(loginModel: LoginModel): Observable<boolean> {
    const data = JSON.stringify(loginModel);
    const options = { headers: { 'Content-Type': 'application/json' } };
    return this.http
      .post<number>(`${this.env.apiUrl}/user/login`, data, options)
      .pipe(
        map(response => {
          this.userId = response;
          this.user = !!response;
          return !!response;
        })
      );
  }

  public updateUser(userSettings: UserSettings): Observable<boolean> {
    const data = JSON.stringify(userSettings);
    const options = { headers: { 'Content-Type': 'application/json' } };
    return this.http.put<boolean>(`${this.env.apiUrl}/user`, data, options);
  }
}
