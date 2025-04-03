package com.dev.task.movie.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PaginationResultDTO<T> {
  private List<T> result;

  @JsonProperty("totalResults")
  int totalResults;

  public List<T> getResult() {
    return result;
  }

  public void setResult(List<T> result) {
    this.result = result;
  }

  public int getTotalResults() {
    return totalResults;
  }

  public void setTotalResults(int totalResults) {
    this.totalResults = totalResults;
  }
}
