package com.tassioauad.moviecheck.view;

import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.MovieWatched;

import java.util.List;

public interface ListMovieWatchedView {
    void showWatchedMovies(List<MovieWatched> movieWatchedList);

    void warnAnyWatchedMovieFounded();

    void warnMovieRemoved(Movie movie);
}
