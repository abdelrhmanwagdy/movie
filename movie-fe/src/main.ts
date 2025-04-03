import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { LoginComponent } from './app/content/login/login.component';
import { importProvidersFrom } from '@angular/core';
import { HttpClientModule, provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { authInterceptor } from './app/interceptors/auth.interceptor';
import { RouterModule } from '@angular/router';
import { AuthGuard } from './app/core/service/guards/auth.guard';
import { routes } from './app/app.routes';
import { AdminMovieComponent } from './app/content/admin-movie/admin-movie.component';
import { RegisterComponent } from './app/content/register/register.component';
import { MovieListComponent } from './app/content/movie/movie-list/movie-list.component';
import { MovieComponent } from './app/content/movie/movie.component';

bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(
      RouterModule.forRoot(routes),  // Set up routing
      HttpClientModule               // Set up HttpClientModule
    ),
    provideHttpClient(withFetch(), withInterceptors([authInterceptor])), // Apply the AuthInterceptor globally
    AuthGuard                      // Provide the AuthGuard if it's used in routing
  ]
}).catch((err) => console.error(err));