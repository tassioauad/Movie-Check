package com.tassioauad.moviecheck.model.dao;

import com.tassioauad.moviecheck.model.entity.Genre;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.MovieWatched;
import com.tassioauad.moviecheck.model.entity.User;

import java.util.List;

public interface MovieWatchedDao extends DaoLoader {
    void listAll(User user);

    MovieWatched findByMovie(Movie movie, User user);

    List<Long> favoriteGenres(User user, int size);

    void remove(MovieWatched movieWatched);

    void remove(Movie movie, User user);

    void insert(MovieWatched movieWatched);

    void update(MovieWatched movieWatched);

    void save(MovieWatched movieWatched);
}
