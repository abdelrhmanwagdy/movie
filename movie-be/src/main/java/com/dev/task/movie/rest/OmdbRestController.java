package com.dev.task.movie.rest;

import com.dev.task.movie.model.dto.ErrorDTO;
import com.dev.task.movie.model.dto.OmdbMovieSearchDTO;
import com.dev.task.movie.model.dto.ResultDTO;
import com.dev.task.movie.model.entity.Movie;
import com.dev.task.movie.service.OmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OmdbRestController{
  private final OmdbService omdbService;

  @Autowired
  public OmdbRestController(OmdbService theOmdbService) {
    this.omdbService = theOmdbService;
  }

  @GetMapping("/omdb/title/{title}")
  public ResponseEntity<ResultDTO<Movie>> getOmdbMovieByTitle(@PathVariable String title) {
    Movie theMovie = omdbService.getOMDBMovieByTitle(title);
    ResultDTO<Movie> resultDTO = new ResultDTO<>();

    if (theMovie == null) {
      ErrorDTO errorDTO = new ErrorDTO();
      errorDTO.setError("Movie not found with title " + title);
      resultDTO.setError(errorDTO);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultDTO);
    }

    resultDTO.setResult(theMovie);
    return ResponseEntity.ok(resultDTO);
  }

  @GetMapping("/omdb/imdb/{imdbid}")
  public ResponseEntity<ResultDTO<Movie>> getOmdbMovieByImdb(@PathVariable String imdbid) {
    Movie theMovie = omdbService.getOMDBMovieByImdbId(imdbid);
    ResultDTO<Movie> resultDTO = new ResultDTO<>();

    if (theMovie == null) {
      ErrorDTO errorDTO = new ErrorDTO();
      errorDTO.setError("Movie not found with imdbID " + imdbid);
      resultDTO.setError(errorDTO);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultDTO);
    }

    resultDTO.setResult(theMovie);
    return ResponseEntity.ok(resultDTO);
  }

  @GetMapping("/omdb/search/{title}")
  public ResponseEntity<ResultDTO<OmdbMovieSearchDTO>> searchOMDB(
      @PathVariable String title,
      @RequestParam int page) {

    OmdbMovieSearchDTO movies = omdbService.searchOmdbMovies(title, page);
    ResultDTO<OmdbMovieSearchDTO> resultDTO = new ResultDTO<>();

    if (movies == null || movies.getTotalResults() == null || Integer.parseInt(movies.getTotalResults()) < 1) {
      ErrorDTO errorDTO = new ErrorDTO();
      errorDTO.setError("Movie not found with title " + title);
      resultDTO.setError(errorDTO);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultDTO);
    }

    resultDTO.setResult(movies);
    return ResponseEntity.ok(resultDTO);
  }
}
