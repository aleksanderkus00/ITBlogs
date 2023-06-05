import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class TokenService {
  constructor(private jwtHelperService: JwtHelperService) {}

  public setToken(token: string): void {
    localStorage.setItem('access_token', token);
  }

  public getToken(): string | null {
    return localStorage.getItem('access_token');
  }

  public clearToken(): void {
    localStorage.removeItem('access_token');
  }

  public isTokenValid(): boolean {
    return !this.jwtHelperService.isTokenExpired(this.getToken());
  }
}
