import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { AuthService } from '../../../core/service/auth.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  constructor(
    private router: Router,
    private authService: AuthService,
  ) {
  }

  get isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  get userRoles(): string[] {
    return this.authService.getUserRoles();
  }

  isAdmin(): boolean {
    return this.userRoles.includes('ROLE_ADMIN');
  }

  isViewer(): boolean {
    return this.userRoles.includes('ROLE_VIEWER');
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}