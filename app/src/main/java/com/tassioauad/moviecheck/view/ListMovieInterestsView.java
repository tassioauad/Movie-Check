package com.tassioauad.moviecheck.view;

import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.MovieInterest;

import java.util.List;

public interface ListMovieInterestsView {
    void showMovieInterests(List<MovieInterest> movieInterestList);

    void warnAnyInterestFounded();

    void warnMovieRemoved(Movie movie);
}
