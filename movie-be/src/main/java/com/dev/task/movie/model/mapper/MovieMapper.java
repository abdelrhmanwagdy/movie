package com.dev.task.movie.model.mapper;

import com.dev.task.movie.model.dto.OmdbMovieDTO;
import com.dev.task.movie.model.dto.OmdbMovieSearchDTO;
import com.dev.task.movie.model.entity.Movie;

import java.util.List;

public interface MovieMapper {
  Movie formOmdbMovie(OmdbMovieDTO omdbMovieDTO);
  List<Movie> formOmdbMovie(OmdbMovieSearchDTO omdbMovieSearchDTO);
  OmdbMovieDTO formMovie(Movie movie);
}
