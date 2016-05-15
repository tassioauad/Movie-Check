package com.tassioauad.moviecheck.view;

import com.tassioauad.moviecheck.model.entity.Genre;

import java.util.Date;
import java.util.List;

public interface MovieDetailView {
    void showVoteCount(long voteCount);

    void showVoteAverage(float voteAverage);

    void showOverview(String overview);

    void showReleaseDate(Date releaseDate);

    void showPoster(String posterUrl);

    void showBackdrop(String backdropUrl);

    void showLoadingGenres();

    void warnFailedOnLoadGenres();

    void showGenres(List<Genre> genreList);

    void warnAnyGenreFounded();

    void hideLoadingGenres();

    void disableToCheckInterest();

    void enableToCheckInterest();

    void checkInterest();

    void uncheckInterest();
}
