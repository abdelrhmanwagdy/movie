package com.dev.task.movie.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;

public class OmdbMovieDTO {
  @JsonProperty("Id")

  private int id;
  @JsonProperty("imdbID")

  private String imdbID;
  @JsonProperty("Title")

  private String title;
  @JsonProperty("Year")

  private String year;
  @JsonProperty("Poster")

  private String poster;

  @JsonProperty("Type")

  private String type;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getImdbID() {
    return imdbID;
  }

  public void setImdbID(String imdbID) {
    this.imdbID = imdbID;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public String getPoster() {
    return poster;
  }

  public void setPoster(String poster) {
    this.poster = poster;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
