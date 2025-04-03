import { HttpClientModule } from '@angular/common/http';
import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MovieService } from '../../core/service/movie.service';
import { CommonModule } from '@angular/common';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { catchError, debounceTime, Observable, of, Subject, switchMap } from 'rxjs';
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
  templateUrl: './admin-movie.component.html',
  styleUrls: ['./admin-movie.component.css']
})
export class AdminMovieComponent implements AfterViewInit {
  query: string = '';
  importMessage: string = '';
  importMessageType: string = '';
  showMessage: boolean = false;
  displayedColumns: string[] = ['poster', 'title', 'year', 'import'];
  dataSource = new MatTableDataSource<any>([]);
  isLoading: boolean = false;
  errorMessage: string = '';
  currentPage: number = 1;
  pageSize: number = 10;
  
  paginatorTotal$: Observable<number> =of(0);
  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;

  private searchQuerySubject = new Subject<string>(); 

  constructor(private movieService: MovieService) {}

  ngOnInit(): void {
    this.searchQuerySubject.pipe(
      debounceTime(500), 
      switchMap(query => {
        if (query.length >= 3) {
          this.isLoading = true;
          this.errorMessage = '';
          return this.movieService.searchMovies(query, this.currentPage).pipe(
            catchError(error => {
              console.error('Error during API call:', error);
              this.dataSource.data = [];
              this.isLoading = false;

              if (error.status === 404) {
                this.errorMessage = 'No movies found!';
              } else {
                this.errorMessage = 'Error fetching movies. Please try again later.';
              }

              return of({ result: { Search: [], totalResults: 0 } });
            })
          );
        } else {
          this.isLoading = false;
          this.dataSource.data = [];
          this.errorMessage = 'Please enter at least 3 characters.';
          return of({ result: { Search: [], totalResults: 0 } });
        }
      })
    ).subscribe(response => {
      if (response && response.result) {
        if (response.result.Search && response.result.Search.length > 0) {
          this.dataSource.data = response.result.Search;
          this.paginatorTotal$ = response.result.totalResults;
          if (this.paginator) {
            this.paginator.length = response.result.totalResults;
            this.paginator.pageIndex = this.currentPage - 1;
          }
        } else {
          this.dataSource.data = [];
          this.errorMessage = 'No movies found!';
          this.paginatorTotal$ = of(0);
        }
      } else {
        this.dataSource.data = [];
        this.errorMessage = 'No movies found!'; 
        this.paginatorTotal$ = of(0);
      }
      this.isLoading = false;
    });
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator!;
  }

  onSearch(): void {
    this.searchQuerySubject.next(this.query);
  }

  pageChanged(event: PageEvent): void {
    this.currentPage = event.pageIndex + 1;
    this.pageSize = event.pageSize;
    this.onSearch(); 
  }

  importMovie(movie: any): void {
    const movieData = {
      imdbID: movie.imdbID, 
      title: movie.Title,
      year: movie.Year,
      poster: movie.Poster,
      type: movie.Type
    };
  
    this.isLoading = true;
    this.movieService.importMovie(movieData).subscribe({
      next: (response) => {
        this.isLoading = false;
  
        if (response.result) {
          this.importMessage = ` Movie "${response.result.title}" imported successfully!`;
          this.importMessageType = 'success';
        } else if (response.error) {
          this.importMessage = `error during importing`;
          this.importMessageType = 'error';
        }
        this.scrollToTop();
        this.showMessage = true;
        this.autoHideMessage();
      },
      error: (err) => {        
        this.isLoading = false;
        this.importMessage = err.error.error.error;
        this.importMessageType = 'error';
        this.showMessage = true;
        this.scrollToTop();
        this.autoHideMessage();
      }
    });
  }

  removeMovie(movie: any): void {
  
    this.isLoading = true;
    this.movieService.removeMovie(movie.imdbID).subscribe({
      next: (response) => {
        this.isLoading = false;
  
        if (response.result) {
          this.importMessage = `Movie deleted successfully!`;
          this.importMessageType = 'success';
        } else if (response.error) {
          this.importMessage = `error during deleting`;
          this.importMessageType = 'error';
        }
        this.scrollToTop();
        this.showMessage = true;
        this.autoHideMessage();
      },
      error: (err) => {        
        this.isLoading = false;
        this.importMessage = err.error.error.error;
        this.importMessageType = 'error';
        this.showMessage = true;
        this.scrollToTop();
        this.autoHideMessage();
      }
    });
  }
  
  autoHideMessage(): void {
    setTimeout(() => {
      this.showMessage = false;
    }, 10000);
  }

  scrollToTop(): void {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

}