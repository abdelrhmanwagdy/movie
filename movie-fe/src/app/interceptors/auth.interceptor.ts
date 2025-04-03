import { HttpRequest, HttpHandlerFn, HttpEvent, HttpErrorResponse, HttpHandler, HttpInterceptor, HttpInterceptorFn } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, Observable, throwError } from 'rxjs';
import { AuthService } from '../core/service/auth.service';

export const authInterceptor: HttpInterceptorFn = (req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> => {
  const authService = inject(AuthService);  
  const router = inject(Router); 

  const token = sessionStorage.getItem('authToken');
  
  const clonedRequest = token ? req.clone({
    setHeaders: { Authorization: `Bearer ${token}` }
  }) : req;

  return next(clonedRequest).pipe(
    catchError((error) => {
      if (error.status === 401) {

        authService.logout();
        router.navigate(['/login']);
      }
      throw error;
    })
  );
};
