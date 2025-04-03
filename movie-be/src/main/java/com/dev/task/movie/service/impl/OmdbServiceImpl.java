package com.dev.task.movie.service.impl;

import com.dev.task.movie.client.OmdbClient;
import com.dev.task.movie.dao.MovieDAO;
import com.dev.task.movie.model.dto.OmdbMovieDTO;
import com.dev.task.movie.model.dto.OmdbMovieSearchDTO;
import com.dev.task.movie.model.entity.Movie;
import com.dev.task.movie.model.mapper.MovieMapper;
import com.dev.task.movie.service.MovieService;
import com.dev.task.movie.service.OmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OmdbServiceImpl implements OmdbService {
  private OmdbClient omdbClient;
  private MovieMapper movieMapper;

  @Autowired
  public OmdbServiceImpl(OmdbClient theOmdbClient, MovieMapper theMovieMapper) {
    omdbClient = theOmdbClient;
    movieMapper = theMovieMapper;
  }
  @Override
  public Movie getOMDBMovieByTitle(String title) {
    OmdbMovieDTO omdbMovieDTO = omdbClient.getMovieByTitle(title);
    return movieMapper.formOmdbMovie(omdbMovieDTO);
  }

  @Override
  public Movie getOMDBMovieByImdbId(String title) {
    OmdbMovieDTO omdbMovieDTO = omdbClient.getMovieByIMDBId(title);
    return movieMapper.formOmdbMovie(omdbMovieDTO);
  }

  @Override
  public OmdbMovieSearchDTO searchOmdbMovies(String title, int page){
    return  omdbClient.searchMovies(title, page);
  }

}
