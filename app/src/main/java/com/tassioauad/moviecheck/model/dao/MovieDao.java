package com.tassioauad.moviecheck.model.dao;

import com.tassioauad.moviecheck.model.entity.Movie;

import java.util.List;

public interface MovieDao {
    void save(Movie movie);

    void update(Movie movie);

    void insert(Movie movie);

    Movie findById(Long id);

    List<Movie> listAll();
}
