package com.dev.task.movie.rest;

import com.dev.task.movie.model.dto.*;
import com.dev.task.movie.model.entity.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dev.task.movie.service.MovieService;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api")
public class MovieRestController{
  private final MovieService movieService;
  private final ObjectMapper objectMapper;

  @Autowired
  public MovieRestController(MovieService theMovieService, ObjectMapper theObjectMapper) {
    this.movieService = theMovieService;
    this.objectMapper = theObjectMapper;
  }

  @GetMapping("/movies")
  public ResponseEntity<ResultDTO<List<Movie>>> findAll() {
    ResultDTO<List<Movie>> resultDTO = new ResultDTO<>();
    resultDTO.setResult(movieService.findAll());

    return ResponseEntity.ok(resultDTO);
  }

  @PostMapping("/paged-movies")
  public ResponseEntity<ResultDTO<PaginationResultDTO<OmdbMovieDTO>>> findAllWithPagination(@RequestBody PaginationDTO paginationDTO) {
    ResultDTO<PaginationResultDTO<OmdbMovieDTO>> resultDTO = new ResultDTO<>();
    resultDTO.setResult(movieService.findAllWithPagination(paginationDTO.getPage(), paginationDTO.getSize()));

    return ResponseEntity.ok(resultDTO);
  }

  @GetMapping("/movies/{movieId}")
  public ResponseEntity<ResultDTO<Movie>> getMovie(@PathVariable int movieId) {
    Movie theMovie = movieService.findById(movieId);
    ResultDTO<Movie> resultDTO = new ResultDTO<>();

    if (theMovie == null) {
      ErrorDTO errorDTO = new ErrorDTO();
      errorDTO.setError("Movie not found with id " + movieId);
      resultDTO.setError(errorDTO);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultDTO);
    }

    resultDTO.setResult(theMovie);
    return ResponseEntity.ok(resultDTO);
  }

  @PostMapping("/movies/import/imdb")
  public ResponseEntity<ResultDTO<Movie>> importMovieByIMDB(@RequestBody Movie theMovie) {
    theMovie.setId(0);

    ResultDTO<Movie> resultDTO = new ResultDTO<>();

    if (theMovie.getImdbID() == null || theMovie.getImdbID().isEmpty()) {
      ErrorDTO errorDTO = new ErrorDTO();
      errorDTO.setError("Movie Imdb is required for importing.");
      resultDTO.setError(errorDTO);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultDTO);
    }

    Movie existingMovie = movieService.findByimdbId(theMovie.getImdbID());
    if (existingMovie != null) {
      ErrorDTO errorDTO = new ErrorDTO();
      errorDTO.setError("Cannot import - Movie with imdb " + theMovie.getImdbID() + "is already exists");
      resultDTO.setError(errorDTO);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultDTO);
    }

    Movie updatedMovie = movieService.save(theMovie);
    resultDTO.setResult(updatedMovie);
    return ResponseEntity.ok(resultDTO);
  }

  @PostMapping("/movies/import/title")
  public ResponseEntity<ResultDTO<Movie>> importMovieByTitle(@RequestBody Movie theMovie) {
    theMovie.setId(0);

    ResultDTO<Movie> resultDTO = new ResultDTO<>();

    if (theMovie.getTitle() == null || theMovie.getTitle().isEmpty()) {
      ErrorDTO errorDTO = new ErrorDTO();
      errorDTO.setError("Movie Title is required for importing.");
      resultDTO.setError(errorDTO);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultDTO);
    }

    List<Movie> existingMovie = movieService.findByTitle(theMovie.getTitle());
    if (existingMovie.size() > 0) {
      ErrorDTO errorDTO = new ErrorDTO();
      errorDTO.setError("Cannot import - Movie with title " + theMovie.getTitle() + "is already exists");
      resultDTO.setError(errorDTO);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultDTO);
    }

    Movie updatedMovie = movieService.save(theMovie);
    resultDTO.setResult(updatedMovie);
    return ResponseEntity.ok(resultDTO);
  }


  @DeleteMapping("/movies/{movieId}")
  public ResponseEntity<ResultDTO<String>> deleteMovie(@PathVariable int movieId) {
    Movie tempMovie = movieService.findById(movieId);
    ResultDTO<String> resultDTO = new ResultDTO<>();

    if (tempMovie == null) {
      ErrorDTO errorDTO = new ErrorDTO();
      errorDTO.setError("Movie id not found - " + movieId);
      resultDTO.setError(errorDTO);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultDTO);
    }

    movieService.deleteById(movieId);
    resultDTO.setResult("Deleted Movie id - " + movieId);
    return ResponseEntity.ok(resultDTO);
  }


  @DeleteMapping("/movies/remove/imdb/{movieImdbId}")
  public ResponseEntity<ResultDTO<String>> deleteMovieByImdbId(@PathVariable String movieImdbId) {

    Movie tempMovie = movieService.findByimdbId(movieImdbId);
    ResultDTO<String> resultDTO = new ResultDTO<>();

    if (tempMovie == null) {
      ErrorDTO errorDTO = new ErrorDTO();
      errorDTO.setError("This Movie is not exists in our Database");
      resultDTO.setError(errorDTO);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultDTO);
    }

    movieService.deleteByImdbId(movieImdbId);
    resultDTO.setResult("Deleted Movie id - " + movieImdbId);
    return ResponseEntity.ok(resultDTO);
  }
}
