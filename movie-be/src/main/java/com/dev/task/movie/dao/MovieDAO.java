package com.dev.task.movie.dao;

import com.dev.task.movie.model.entity.Movie;

import java.util.List;

public interface MovieDAO {
  Movie save(Movie movie);

  Movie findById(Integer id);

  List<Movie> findAll();
  List<Movie> findAllWithPagination(int page, int size);
  long countAll();
  List<Movie> findByTitle(String title);
  Movie findByimdbId(String imdbId);

  void deleteById(Integer id);

  void deleteByImdbId(String imdbId);
}
