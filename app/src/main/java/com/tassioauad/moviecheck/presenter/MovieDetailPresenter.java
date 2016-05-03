package com.tassioauad.moviecheck.presenter;


import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.view.MovieDetailView;

public class MovieDetailPresenter {

    private MovieDetailView view;

    public MovieDetailPresenter(MovieDetailView view) {
        this.view = view;
    }

    public void init(Movie movie) {
        view.showVoteCount(movie.getVoteCount());
        view.showVoteAverage(movie.getVoteAverage());
        view.showOverview(movie.getOverview());
        view.showReleaseDate(movie.getReleaseDate());
        view.showPoster(movie.getPosterUrl());
        view.showBackdrop(movie.getBackdropUrl());
    }

}
