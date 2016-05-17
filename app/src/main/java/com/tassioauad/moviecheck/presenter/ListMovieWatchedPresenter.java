package com.tassioauad.moviecheck.presenter;

import com.tassioauad.moviecheck.model.dao.MovieWatchedDao;
import com.tassioauad.moviecheck.model.dao.UserDao;
import com.tassioauad.moviecheck.model.entity.MovieWatched;
import com.tassioauad.moviecheck.view.ListMovieWatchedView;

import java.util.List;

public class ListMovieWatchedPresenter {

    private ListMovieWatchedView view;
    private MovieWatchedDao movieWatchedDao;
    private UserDao userDao;

    public ListMovieWatchedPresenter(ListMovieWatchedView view, MovieWatchedDao movieWatchedDao, UserDao userDao) {
        this.view = view;
        this.movieWatchedDao = movieWatchedDao;
        this.userDao = userDao;
    }

    public void loadMovieInterests() {
        List<MovieWatched> movieWatchedList = movieWatchedDao.listAll(userDao.getLoggedUser());
        if(movieWatchedList.size() == 0) {
            view.warnAnyWatchedMovieFounded();
        } else {
            view.showWatchedMovies(movieWatchedList);
        }
    }

    public void remove(MovieWatched movieWatched) {
        movieWatchedDao.remove(movieWatched);
    }
}
