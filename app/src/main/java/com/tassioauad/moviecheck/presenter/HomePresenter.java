package com.tassioauad.moviecheck.presenter;

import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.moviecheck.model.dao.UserDao;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.User;
import com.tassioauad.moviecheck.view.HomeView;

import java.util.List;

public class HomePresenter {

    HomeView view;
    private MovieApi movieApi;
    private UserDao userDao;

    public HomePresenter(HomeView view, MovieApi movieApi, UserDao userDao) {
        this.view = view;
        this.movieApi = movieApi;
        this.userDao = userDao;
    }

    public void init() {
        User loggedUser = userDao.getLoggedUser();
        if (loggedUser != null) {
            view.showLoggedUser(userDao.getLoggedUser());
        }

        if(!userDao.hasReadTutorial()) {
            view.showTutorial();
        }
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

    public void login(User user) {
        userDao.login(user);
        view.showLoggedUser(user);
    }

    public void stop() {
        movieApi.cancelAllServices();
    }

    public void logout() {
        userDao.logout();
        view.warnUserDesconnected();
    }

    public void informUserHasReadTutorial() {
        userDao.informHasReadTutorial();
    }
}
