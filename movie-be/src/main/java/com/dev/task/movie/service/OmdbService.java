package com.dev.task.movie.service;

import com.dev.task.movie.model.dto.OmdbMovieSearchDTO;
import com.dev.task.movie.model.entity.Movie;
public interface OmdbService {
  Movie getOMDBMovieByTitle(String title);
  Movie getOMDBMovieByImdbId(String title);
  OmdbMovieSearchDTO searchOmdbMovies(String title, int page);
}
