package com.dev.task.movie.client;

import com.dev.task.movie.config.OmdbFeignClientConfig;
import com.dev.task.movie.model.dto.OmdbMovieDTO;
import com.dev.task.movie.model.dto.OmdbMovieSearchDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "omdbClient", url = "http://www.omdbapi.com", configuration = OmdbFeignClientConfig.class)
public interface OmdbClient {

  @GetMapping
  OmdbMovieDTO getMovieByTitle(@RequestParam("t") String title);

  @GetMapping
  OmdbMovieDTO getMovieByIMDBId(@RequestParam("i") String imdbId);

  @GetMapping
  OmdbMovieSearchDTO searchMovies(@RequestParam("s") String title, @RequestParam("page") int page);
}
