package com.tassioauad.moviecheck.view;

import com.tassioauad.moviecheck.model.entity.Cast;
import com.tassioauad.moviecheck.model.entity.Crew;
import com.tassioauad.moviecheck.model.entity.Movie;

import java.util.List;

public interface PersonWorkView {
    void hideLoadingWorkAsCrew();

    void showLoadingWorkAsCrew();

    void showWorksAsCrew(List<Movie> crewMovieList);

    void warnAnyWorkAsCrewFounded();

    void warnFailedToLoadWorkAsCrew();

    void showLoadingWorkAsCast();

    void warnAnyWorkAsCastFounded();

    void showWorksAsCast(List<Movie> castMovieList);

    void hideLoadingWorkAsCast();

    void warnFailedToLoadWorkAsCast();
}
