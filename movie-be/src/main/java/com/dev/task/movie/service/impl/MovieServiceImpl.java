package com.dev.task.movie.service.impl;

import com.dev.task.movie.model.dto.OmdbMovieDTO;
import com.dev.task.movie.model.dto.PaginationResultDTO;
import com.dev.task.movie.model.entity.Movie;
import com.dev.task.movie.model.mapper.MovieMapper;
import com.dev.task.movie.service.MovieService;
import com.dev.task.movie.dao.MovieDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
  private MovieDAO movieDAO;
  private MovieMapper movieMapper;

  @Autowired
  public MovieServiceImpl(MovieDAO theMovieDAO, MovieMapper theMovieMapper) {
    movieDAO = theMovieDAO;
    movieMapper = theMovieMapper;
  }

  @Override
  public List<Movie> findAll() {
    return movieDAO.findAll();
  }

  @Override
  public PaginationResultDTO<OmdbMovieDTO> findAllWithPagination(int page, int size) {
    int totalcount = Math.toIntExact(movieDAO.countAll());
    List<Movie> movies = movieDAO.findAllWithPagination(page, size);

    List<OmdbMovieDTO> omdbMovieDTOList = new ArrayList<>();
    for (Movie movie : movies) {
      omdbMovieDTOList.add(movieMapper.formMovie(movie));
    }

    PaginationResultDTO<OmdbMovieDTO> paginationResultDTO = new PaginationResultDTO();
    paginationResultDTO.setTotalResults(totalcount);
    paginationResultDTO.setResult(omdbMovieDTOList);

    return paginationResultDTO;
  }

  @Override
  public Movie findById(int id) {
    return movieDAO.findById(id);
  }

  @Override
  public List<Movie> findByTitle(String title) {
    return movieDAO.findByTitle(title);
  }

  @Override
  public Movie findByimdbId(String imdbId) {
    Movie movies = movieDAO.findByimdbId(imdbId);
    return movies;
  }

  @Transactional
  @Override
  public Movie save(Movie movie) {
    return movieDAO.save(movie);
  }

  @Transactional
  @Override
  public void deleteById(int id) {
    movieDAO.deleteById(id);
  }

  @Transactional
  @Override
  public void deleteByImdbId(String imdbId) {
    movieDAO.deleteByImdbId(imdbId);
  }

}
