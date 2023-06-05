import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { RegisterModel } from '../models/register.moder';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { LoginModel } from '../models/login.model';
import { UserSettings } from '../models/user.settings';
import { ToastrService } from 'ngx-toastr';
import { AuthenticatedResponse } from '../models/authenticatedResponse.model';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private env = environment;
  private userId = 0;
  public user = false;
  public userRoles: string[] = [];
  private likedArticlesIds: number[] = [];
  private savedArticlesIds: number[] = [];

  constructor(
    private http: HttpClient,
    private tokenService: TokenService,
    private tostrService: ToastrService
  ) {
    if (this.tokenService.isTokenValid()) {
      this.user = true;
      this.userId = this.getUserIdFromLocalStorage();
    }
    this.setLikedArticlesIds();
    this.setSavedArticlesIds();
  }

  public getUserId(): number {
    if (this.userId == null) {
      this.userId = this.getUserIdFromLocalStorage();
    }
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
      .post<number>(`${this.env.apiUrl}/auth/register`, data, options)
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
      .post<AuthenticatedResponse>(
        `${this.env.apiUrl}/auth/login`,
        data,
        options
      )
      .pipe(
        map(response => {
          this.userId = response.id;
          this.user = !!response;
          this.userRoles = response.roles;
          this.tokenService.setToken(response.token);
          this.setUserIdToLocalStorage(response.id);
          return !!response;
        })
      );
  }

  public updateUser(userSettings: UserSettings): Observable<boolean> {
    const data = JSON.stringify(userSettings);
    const options = { headers: { 'Content-Type': 'application/json' } };
    return this.http.put<boolean>(`${this.env.apiUrl}/user`, data, options);
  }

  public logout(): void {
    this.tokenService.clearToken();
    this.user = false;
  }

  public isArticleLiked(articleId: number | undefined): boolean {
    if (articleId == undefined) return false;
    return this.likedArticlesIds.includes(articleId);
  }

  public isArticleSaved(articleId: number | undefined): boolean {
    if (articleId == undefined) return false;
    return this.savedArticlesIds.includes(articleId);
  }

  private getUserIdFromLocalStorage(): number {
    return Number(localStorage.getItem('user_id'));
  }

  private setUserIdToLocalStorage(userId: number): void {
    localStorage.setItem('user_id', String(userId));
  }

  private setLikedArticlesIds(): void {
    if (this.getUserId() === 0) {
      this.likedArticlesIds = [];
      return;
    }
    this.http
      .get<number[]>(`${this.env.apiUrl}/article/userLikes/${this.getUserId()}`)
      .subscribe(res => {
        this.likedArticlesIds = res;
      });
  }

  private setSavedArticlesIds(): void {
    if (this.getUserId() === 0) {
      this.savedArticlesIds = [];
      return;
    }
    this.http
      .get<number[]>(`${this.env.apiUrl}/article/userSaves/${this.getUserId()}`)
      .subscribe(res => {
        this.savedArticlesIds = res;
      });
  }
}
