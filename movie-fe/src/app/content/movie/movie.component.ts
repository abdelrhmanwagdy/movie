import { Component } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { MovieService } from '../../core/service/movie.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-movie',
  standalone: true,
  imports: [ CommonModule, RouterModule ],
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css']
})
export class MovieComponent {
    movie: any;
    isLoading: boolean = false;
  
    constructor(private route: ActivatedRoute, private movieService: MovieService) {}
  
    ngOnInit() {
      const movieId = this.route.snapshot.paramMap.get('id');
      if (movieId) {
        this.fetchMovieDetails(movieId);
      }
    }
  
    fetchMovieDetails(id: string) {
      this.isLoading = true;
      this.movieService.getMovieById(id).subscribe(
        response => {
          this.movie = response.result ;
          this.isLoading = false;
        },
        error => {
          console.error('Error fetching movie details:', error);
          this.isLoading = false;
        }
      );
    }

    
  }
  