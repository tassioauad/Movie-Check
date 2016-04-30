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
        listUpcomingMovies();
        listPopularMovies();
        listTopRatedMovies();
        listNowPlayingMovies();
    }

    public void listUpcomingMovies() {
        view.showLoadingUpcomingMovies();
        movieApi.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Movie> movieList = (List<Movie>) object;
                if (movieList == null || movieList.size() == 0) {
                    view.warnAnyUpcomingMovieFounded();
                } else {
                    view.showUpcomingMovies(movieList);
                }
                view.hideLoadingUpcomingMovies();
            }

            @Override
            public void onException(Exception exception) {
                view.warnFailedToLoadUpcomingMovies();
                view.hideLoadingUpcomingMovies();
            }
        });

        movieApi.listUpcomingMovies();
    }

    public void listPopularMovies() {
        view.showLoadingPopularMovies();
        movieApi.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Movie> movieList = (List<Movie>) object;
                if (movieList == null || movieList.size() == 0) {
                    view.warnAnyPopularMovieFounded();
                } else {
                    view.showPopularMovies(movieList);
                }
                view.hideLoadingPopularMovies();
            }

            @Override
            public void onException(Exception exception) {
                view.warnFailedToLoadPopularMovies();
                view.hideLoadingPopularMovies();
            }
        });

        movieApi.listPopularMovies();
    }

    public void listTopRatedMovies() {
        view.showLoadingTopRatedMovies();
        movieApi.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Movie> movieList = (List<Movie>) object;
                if (movieList == null || movieList.size() == 0) {
                    view.warnAnyTopRatedMovieFounded();
                } else {
                    view.showTopRatedMovies(movieList);
                }
                view.hideLoadingTopRatedMovies();
            }

            @Override
            public void onException(Exception exception) {
                view.warnFailedToLoadTopRatedMovies();
                view.hideLoadingTopRatedMovies();
            }
        });

        movieApi.listTopRatedMovies();
    }

    public void listNowPlayingMovies() {
        view.showLoadingNowPlayingMovies();
        movieApi.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Movie> movieList = (List<Movie>) object;
                if (movieList == null || movieList.size() == 0) {
                    view.warnAnyNowPlayingMovieFounded();
                } else {
                    view.showNowPlayingMovies(movieList);
                }
                view.hideLoadingNowPlayingMovies();
            }

            @Override
            public void onException(Exception exception) {
                view.warnFailedToLoadNowPlayingMovies();
                view.hideLoadingNowPlayingMovies();
            }
        });

        movieApi.listNowPlayingMovies();
    }
}
