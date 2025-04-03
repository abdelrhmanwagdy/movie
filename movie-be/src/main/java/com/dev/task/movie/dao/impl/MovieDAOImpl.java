package com.dev.task.movie.dao.impl;

import com.dev.task.movie.dao.MovieDAO;
import com.dev.task.movie.model.entity.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieDAOImpl implements MovieDAO {
  private EntityManager entityManager;

  @Autowired
  public MovieDAOImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Movie save(Movie movie) {
    Movie dbMovie = entityManager.merge(movie);
    return dbMovie;
  }
  @Override
  public Movie findById(Integer id) {
    return entityManager.find(Movie.class, id);
  }

  @Override
  public List<Movie> findAll() {
    TypedQuery<Movie> query = entityManager.createQuery("FROM Movie", Movie.class);

    return query.getResultList();
  }

  @Override
  public List<Movie> findAllWithPagination(int page, int size) {
    TypedQuery<Movie> query = entityManager.createQuery("FROM Movie", Movie.class);

    query.setFirstResult((page - 1) * size);
    query.setMaxResults(size);

    return query.getResultList();
  }

  @Override
  public long countAll() {
    return entityManager.createQuery("SELECT COUNT(m) FROM Movie m", Long.class)
        .getSingleResult();
  }

  @Override
  public List<Movie> findByTitle(String title) {
    TypedQuery<Movie> query = entityManager.createQuery(
        "FROM Movie WHERE title=:data", Movie.class);

    query.setParameter("data", title);

    return query.getResultList();
  }

  @Override
  public Movie findByimdbId(String imdbId) {
    TypedQuery<Movie> query = entityManager.createQuery("FROM Movie WHERE imdbID=:data", Movie.class);
    query.setParameter("data", imdbId);
    List<Movie> result = query.getResultList();
    return result.isEmpty() ? null : result.get(0);
  }

  @Override
  public void deleteById(Integer id) {
    Movie movie = entityManager.find(Movie.class, id);
    entityManager.remove(movie);
  }

  @Override
  public void deleteByImdbId(String imdbId) {
    entityManager.createQuery("DELETE FROM Movie WHERE imdbID=:data")
        .setParameter("data", imdbId)
        .executeUpdate();
  }
}
