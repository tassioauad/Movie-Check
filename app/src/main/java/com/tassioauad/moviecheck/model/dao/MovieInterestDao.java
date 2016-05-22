package com.tassioauad.moviecheck.model.dao;

import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.MovieInterest;
import com.tassioauad.moviecheck.model.entity.User;

import java.util.List;

public interface MovieInterestDao extends DaoLoader {
    void listAll(User user);

    List<MovieInterest> listAllUpcoming(User user);

    MovieInterest findByMovie(Movie movie, User user);

    void remove(MovieInterest movieInterest);

    void remove(Movie movie, User user);

    void insert(MovieInterest movieInterest);
}
