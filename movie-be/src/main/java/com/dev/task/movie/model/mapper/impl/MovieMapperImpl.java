package com.dev.task.movie.model.mapper.impl;

import com.dev.task.movie.model.dto.OmdbMovieDTO;
import com.dev.task.movie.model.dto.OmdbMovieSearchDTO;
import com.dev.task.movie.model.entity.Movie;
import com.dev.task.movie.model.mapper.MovieMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieMapperImpl implements MovieMapper {
  @Override
  public Movie formOmdbMovie(OmdbMovieDTO omdbMovieDTO) {
    Movie movie = new Movie();
    movie.setImdbID(omdbMovieDTO.getImdbID());
    movie.setTitle(omdbMovieDTO.getTitle());
    movie.setYear(omdbMovieDTO.getYear());
    movie.setPoster(omdbMovieDTO.getPoster());
    movie.setType(omdbMovieDTO.getType());

    return movie;
  }

  public OmdbMovieDTO formMovie(Movie movie) {
    OmdbMovieDTO omdbMovieDTO = new OmdbMovieDTO();
    omdbMovieDTO.setImdbID(movie.getImdbID());
    omdbMovieDTO.setId(movie.getId());
    omdbMovieDTO.setTitle(movie.getTitle());
    omdbMovieDTO.setYear(movie.getYear());
    omdbMovieDTO.setPoster(movie.getPoster());
    omdbMovieDTO.setType(movie.getType());

    return omdbMovieDTO;
  }

  @Override
  public List<Movie> formOmdbMovie(OmdbMovieSearchDTO omdbMovieSearchDTO) {
    List<Movie> movies = new ArrayList<>();

    if (omdbMovieSearchDTO != null && omdbMovieSearchDTO.getSearch() != null) {
      for (OmdbMovieDTO omdbMovieDTO : omdbMovieSearchDTO.getSearch()) {
        Movie movie = new Movie();
        movie.setImdbID(omdbMovieDTO.getImdbID());
        movie.setTitle(omdbMovieDTO.getTitle());
        movie.setYear(omdbMovieDTO.getYear());
        movie.setPoster(omdbMovieDTO.getPoster());
        movie.setType(omdbMovieDTO.getType());
        movies.add(movie);
      }
    }

    return movies;
  }
}
