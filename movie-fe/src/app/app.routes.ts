import { Routes } from '@angular/router';
import { LoginComponent } from './content/login/login.component';
import { AdminMovieComponent } from './content/admin-movie/admin-movie.component';
import { AuthGuard } from './core/service/guards/auth.guard';
import { RegisterComponent } from './content/register/register.component';
import { MovieListComponent } from './content/movie/movie-list/movie-list.component';
import { MovieComponent } from './content/movie/movie.component';


export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { 
    path: 'admin-movies', 
    component: AdminMovieComponent, 
    canActivate: [AuthGuard], 
    data: { roles: ['ROLE_ADMIN'] }
  },
  { 
    path: 'movies', 
    component: MovieListComponent, 
    canActivate: [AuthGuard], 
    data: { roles: ['ROLE_USER', 'ROLE_ADMIN'] } 
  }, 
  { path: 'movie/:id', component: MovieComponent },
  { path: '**', redirectTo: '/movies' },
];