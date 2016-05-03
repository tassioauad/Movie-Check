package com.tassioauad.moviecheck.view;

import java.util.Date;

public interface MovieDetailView {
    void showVoteCount(long voteCount);

    void showVoteAverage(float voteAverage);

    void showOverview(String overview);

    void showReleaseDate(Date releaseDate);

    void showPoster(String posterUrl);

    void showBackdrop(String backdropUrl);
}
