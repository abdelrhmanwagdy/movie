package com.dev.task.movie.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name="movie")
  public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="imdb_id")
    private String imdbID;

    @Column(name="title")
    private String title;

    @Column(name="year")
    private String year;

    @Column(name="poster")
    private String poster;

    @Column(name="type")
    private String type;

    public Movie() {
    }

  public Movie(String imdbID, String title, String year, String poster, String type) {
    this.imdbID = imdbID;
    this.title = title;
    this.year = year;
    this.poster = poster;
    this.type = type;
  }

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

  @Override
  public String toString() {
    return "Movie{" + "id=" + id + ", imdbID='" + imdbID + '\'' + ", title='" + title + '\'' + ", year='" + year + '\'' + ", poster='" + poster + '\'' + ", type='" + type + '\'' + '}';
  }
}
