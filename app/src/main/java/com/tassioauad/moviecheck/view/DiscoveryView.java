package com.tassioauad.moviecheck.view;

import com.tassioauad.moviecheck.model.entity.Movie;

import java.util.List;

public interface DiscoveryView {
    void showMovie(Movie movie, int index);

    void movieLoaded(List<Movie> movieList, int page);

    void warnWasNotPossibleToLoadMoreMovies();

    void warnFailedToLoadMoreMovies();

    void showLoading();

    void hideLoading();

    void checkInterest();

    void uncheckInterest();
}
