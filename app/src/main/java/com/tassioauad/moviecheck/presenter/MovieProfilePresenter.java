package com.tassioauad.moviecheck.presenter;

import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.view.MovieProfileView;

public class MovieProfilePresenter {

    private MovieProfileView view;

    public void init(Movie movie) {
        view.showMovieName(movie.getTitle());
    }
}
