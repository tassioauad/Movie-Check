package com.tassioauad.moviecheck.model.dao;

import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.MovieWatched;
import com.tassioauad.moviecheck.model.entity.User;

import java.util.List;

public interface MovieWatchedDao {
    List<MovieWatched> listAll(User user);

    MovieWatched findByMovie(Movie movie, User user);

    void remove(MovieWatched movieWatched);

    void insert(MovieWatched movieWatched);

    void update(MovieWatched movieWatched);

    void save(MovieWatched movieWatched);
}
