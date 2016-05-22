package com.tassioauad.moviecheck.view;

import com.tassioauad.moviecheck.model.entity.Movie;

import java.util.List;

public interface SearchMovieView {
    void showLoadingMovies();

    void warnAnyMovieFounded();

    void showMovies(List<Movie> movieList);

    void hideLoadingMovies();

    void warnFailedToLoadMovies();
}
