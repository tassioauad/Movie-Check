package com.tassioauad.moviecheck.model.dao;

import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.MovieInterest;
import com.tassioauad.moviecheck.model.entity.MovieNotInterest;
import com.tassioauad.moviecheck.model.entity.User;

import java.util.List;

public interface MovieNotInterestDao {
    List<MovieNotInterest> listAll(User user);

    List<MovieNotInterest> listAllUpcoming(User user);

    MovieNotInterest findByMovie(Movie movie, User user);

    void remove(MovieNotInterest movieNotInterest);

    void remove(Movie movie, User user);

    void insert(MovieNotInterest movieNotInterest);
}
