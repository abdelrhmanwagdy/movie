import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';
import { LoginResponse } from '../model/login-response.mode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/';
  private tokenKey = 'authToken';

  constructor(private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object) { }

    register(payload: any): Observable<any> {
      return this.http.post<LoginResponse>(`${this.apiUrl}register`, payload).pipe(
        tap((response: LoginResponse) => {
          if (isPlatformBrowser(this.platformId)) {
            sessionStorage.setItem(this.tokenKey, response.token);
            sessionStorage.setItem('username', response.username);
            sessionStorage.setItem('roles', JSON.stringify(response.roles || [])); 
          }
        })
      );
    }

    login(payload: any): Observable<any> {
      return this.http.post<LoginResponse>(`${this.apiUrl}login`, payload).pipe(
        tap((response: LoginResponse) => {
          if (isPlatformBrowser(this.platformId)) {
            sessionStorage.setItem(this.tokenKey, response.token);
            sessionStorage.setItem('username', response.username);
            sessionStorage.setItem('roles', JSON.stringify(response.roles || []));
          }
        })
      );
    }

    logout() {
      if (isPlatformBrowser(this.platformId)) {
        sessionStorage.removeItem(this.tokenKey);
        sessionStorage.removeItem('username');
        sessionStorage.clear();
      }
    }

    isAuthenticated(): boolean {
      if (isPlatformBrowser(this.platformId)) {
        return sessionStorage.getItem(this.tokenKey) !== null;
      }
      return false;
    }
  
    getUserRoles(): string[] {
      if (isPlatformBrowser(this.platformId)) {
        const roles = sessionStorage.getItem('roles');
        return roles ? JSON.parse(roles) : [];
      }
      return [];
    }
  }
