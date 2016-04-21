package com.tassioauad.moviecheck.view;

import com.tassioauad.moviecheck.model.entity.Movie;

import java.util.ArrayList;
import java.util.List;

public interface HomeView {

    void showLoadingUpcomingMovies();

    void hideLoadingUpcomingMovies();

    void showUpcomingMovies(List<Movie> movieList);

    void warnFailedOnLoadUpcomingMovies();

    void warnAnyUpcomingMovieFounded();

    void showLoadingPopularMovies();

    void hideLoadingPopularMovies();

    void warnAnyPopularMovieFounded();

    void warnFailedOnLoadPopularMovies();

    void showPopularMovies(List<Movie> movieList);

    void showLoadingTopRatedMovies();

    void warnAnyTopRatedMovieFounded();

    void showTopRatedMovies(List<Movie> movieList);

    void hideLoadingTopRatedMovies();

    void warnFailedOnLoadTopRatedMovies();

    void showLoadingNowPlayingMovies();

    void warnAnyNowPlayingMovieFounded();

    void showNowPlayingMovies(List<Movie> movieList);

    void hideLoadingNowPlayingMovies();

    void warnFailedOnLoadNowPlayingMovies();
}
