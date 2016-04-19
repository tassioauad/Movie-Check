package com.tassioauad.moviecheck.view;

import com.tassioauad.moviecheck.model.entity.Movie;

import java.util.List;

public interface HomeView {

    void showLoadingUpcomingMovies();

    void hideLoadingUpcomingMovies();

    void showUpComingMovies(List<Movie> movieList);

    void warnFailedOnLoadUpcomingMovies();

    void warnAnyUpcomingMovieFounded();
}
