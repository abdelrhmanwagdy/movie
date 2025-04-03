package com.dev.task.movie.service;

import com.dev.task.movie.model.dto.OmdbMovieDTO;
import com.dev.task.movie.model.dto.OmdbMovieSearchDTO;
import com.dev.task.movie.model.dto.PaginationResultDTO;
import com.dev.task.movie.model.entity.Movie;

import java.util.List;

public interface MovieService {

  List<Movie> findAll();
  PaginationResultDTO<OmdbMovieDTO> findAllWithPagination(int page, int size);
  Movie findById(int id);
  List<Movie> findByTitle(String title);
  Movie findByimdbId(String imdbId);
  Movie save(Movie movie);
  void deleteById(int id);
  void deleteByImdbId(String imdbId);

}
