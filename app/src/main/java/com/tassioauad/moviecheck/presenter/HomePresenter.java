package com.tassioauad.moviecheck.presenter;

import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.view.HomeView;

import java.util.List;

public class HomePresenter {

    HomeView view;
    private MovieApi movieApi;

    public HomePresenter(HomeView view, MovieApi movieApi) {
        this.view = view;
        this.movieApi = movieApi;
    }


    public void init() {
        listUpComingMovies();
    }

    public void listUpComingMovies() {
        view.showLoadingUpcomingMovies();
        movieApi.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Movie> movieList = (List<Movie>) object;
                if (movieList == null || movieList.size() == 0) {
                    view.warnAnyUpcomingMovieFounded();
                } else {
                    view.showUpComingMovies(movieList);
                }
                view.hideLoadingUpcomingMovies();
            }

            @Override
            public void onException(Exception exception) {
                view.warnFailedOnLoadUpcomingMovies();
                view.hideLoadingUpcomingMovies();
            }
        });

        movieApi.listUpcomingMovies();
    }
}
