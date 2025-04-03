import { HttpClientModule } from '@angular/common/http';
import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MovieService } from '../../../core/service/movie.service';
import { CommonModule } from '@angular/common';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { AuthService } from '../../../core/service/auth.service';

@Component({
  selector: 'app-movie',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatInputModule,
    HttpClientModule, 
    FormsModule, 
    CommonModule
  ],
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements AfterViewInit{

  displayedColumns: string[] = ['poster', 'title', 'year', 'actions'];
  dataSource = new MatTableDataSource<any>([]);
  isLoading: boolean = false;
  showMessage: boolean = false;
  userRole: string[] = [];

  currentPage: number = 1;
  paginatorTotal$!: Observable<number>;
  errorMessage: string = '';
  removeMessageType: string = '';


  @ViewChild(MatPaginator) paginator: MatPaginator | null = null;

  constructor(private movieService: MovieService, private router: Router, private authService: AuthService) {}

  ngOnInit() {
    this.userRole = this.authService.getUserRoles();    
    this.loadMovies();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator!;
  }

  loadMovies() {
    const movieData = {
      page: this.currentPage,
      size: 10,
    };
  
    this.isLoading = true;
    this.movieService.getPaginatedMovies(movieData).subscribe(
      response => {        
        if (response.result && response.result.result) {          
          this.dataSource.data = response.result.result;
          this.paginatorTotal$ = response.result.totalResults;
        } else {
          this.dataSource.data = []; 
          this.paginatorTotal$ = of(0);
        }        
        this.isLoading = false;
      },
      error => {
        console.error('Error fetching movies:', error);
        this.isLoading = false;
      }
    );
  }

  viewDetails(movieId: string) {
    
    this.router.navigate(['/movie', movieId]);
  }

  removeMovie(movieId: any): void {
    if (!this.userRole.includes('ROLE_ADMIN')) {
      this.errorMessage = 'You do not have permission to delete movies.';
      this.removeMessageType = 'error';
      this.showMessage = true;
      this.autoHideMessage();
      return;
    }

    this.isLoading = true;
    this.movieService.removeMovieById(movieId).subscribe({
      next: (response) => {
        this.isLoading = false;
        if (response.result) {
          this.errorMessage = `Movie deleted successfully!`;
          this.removeMessageType = `sucess`;
          this.loadMovies();
        } else if (response.error) {
          this.errorMessage = `failed to delete movie`;
          this.removeMessageType = `error`;     
        }
        this.scrollToTop();
        this.showMessage = true;
        this.autoHideMessage();

      },
      error: (err) => {        
        this.isLoading = false;
          this.errorMessage = `failed to delete movie`;
          this.removeMessageType = `error`;
          this.scrollToTop();
          this.showMessage = true;
          this.autoHideMessage();
      }
    });
  }

  autoHideMessage(): void {
    setTimeout(() => {
      this.showMessage = false;
    }, 10000);
  }

  pageChanged(event: any): void {
    this.currentPage = event.pageIndex + 1; 
    this.loadMovies();
  }

  scrollToTop(): void {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
  
}
