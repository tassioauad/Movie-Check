package com.tassioauad.moviecheck.presenter;

import com.tassioauad.moviecheck.model.api.CastApi;
import com.tassioauad.moviecheck.model.api.CrewApi;
import com.tassioauad.moviecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.Person;
import com.tassioauad.moviecheck.view.PersonWorkView;

import java.util.List;

public class PersonWorkPresenter {

    private PersonWorkView view;
    private CastApi castApi;
    private CrewApi crewApi;

    public PersonWorkPresenter(PersonWorkView view, CastApi castApi, CrewApi crewApi) {
        this.view = view;
        this.castApi = castApi;
        this.crewApi = crewApi;
    }

    public void loadCrewWorks(Person person) {
        view.showLoadingWorkAsCrew();
        crewApi.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Movie> movieList = (List<Movie>) object;
                if (movieList == null || movieList.size() == 0) {
                    view.warnAnyWorkAsCrewFounded();
                } else {
                    view.showWorksAsCrew(movieList);
                }
                view.hideLoadingWorkAsCrew();
            }

            @Override
            public void onException(Exception exception) {
                view.warnFailedToLoadWorkAsCrew();
                view.hideLoadingWorkAsCrew();
            }
        });
        crewApi.listMoviesByCrew(person);
    }

    public void loadCastWorks(Person person) {
        view.showLoadingWorkAsCast();
        castApi.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Movie> movieList = (List<Movie>) object;
                if (movieList == null || movieList.size() == 0) {
                    view.warnAnyWorkAsCastFounded();
                } else {
                    view.showWorksAsCast(movieList);
                }
                view.hideLoadingWorkAsCast();
            }

            @Override
            public void onException(Exception exception) {
                view.warnFailedToLoadWorkAsCast();
                view.hideLoadingWorkAsCast();
            }
        });
        castApi.listMoviesByCast(person);
    }

    public void stop() {
        castApi.cancelAllServices();
        crewApi.cancelAllServices();
    }
}
