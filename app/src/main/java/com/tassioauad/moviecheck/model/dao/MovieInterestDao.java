package com.tassioauad.moviecheck.model.dao;

import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.MovieInterest;

import java.util.List;

public interface MovieInterestDao {
    List<MovieInterest> listAll();

    MovieInterest findByMovie(Movie movie);

    void remove(MovieInterest movieInterest);

    void insert(MovieInterest movieInterest);
}
