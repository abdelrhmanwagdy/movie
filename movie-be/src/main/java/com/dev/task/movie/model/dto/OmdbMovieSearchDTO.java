package com.dev.task.movie.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OmdbMovieSearchDTO {
  @JsonProperty("Search")
  List<OmdbMovieDTO> search;
  @JsonProperty("totalResults")
  String totalResults;

  public List<OmdbMovieDTO> getSearch() {
    return search;
  }

  public void setSearch(List<OmdbMovieDTO> search) {
    this.search = search;
  }

  public String getTotalResults() {
    return totalResults;
  }

  public void setTotalResults(String totalResults) {
    this.totalResults = totalResults;
  }
}
