import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { isPlatformBrowser } from '@angular/common'; // Import this
import { Inject, PLATFORM_ID } from '@angular/core'; // Inject PLATFORM_ID
import { AuthService } from '../auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router, private authService: AuthService,@Inject(PLATFORM_ID) private platformId: any) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    if (isPlatformBrowser(this.platformId)) {
      const isAuthenticated = this.authService.isAuthenticated();
      const userRoles = this.authService.getUserRoles();
      const requiredRoles = route.data['roles'] as string[];

      if (isAuthenticated && route.routeConfig?.path === 'login') {
        this.router.navigate(['/movies']);
        return false;
      }

      if (!isAuthenticated && route.routeConfig?.path !== 'login') {
        this.router.navigate(['/login']);
        return false;
      }
      
      if (requiredRoles && !requiredRoles.some(role => userRoles.includes(role))) {
        this.router.navigate(['/login']);
        return false;
      }

      return true;
    }

    return false;
  }
}