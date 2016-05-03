package com.tassioauad.moviecheck.view;

import com.tassioauad.moviecheck.model.entity.Cast;
import com.tassioauad.moviecheck.model.entity.Crew;

import java.util.List;

public interface CastCrewView {
    void hideLoadingCrew();

    void showLoadingCrew();

    void showCrews(List<Crew> crewList);

    void warnAnyCrewFounded();

    void warnFailedToLoadCrews();

    void showLoadingCast();

    void warnAnyCastFounded();

    void showCasts(List<Cast> crewList);

    void hideLoadingCast();

    void warnFailedToLoadCasts();
}
