package com.tassioauad.moviecheck.presenter;

import com.tassioauad.moviecheck.model.dao.MovieInterestDao;
import com.tassioauad.moviecheck.model.dao.UserDao;
import com.tassioauad.moviecheck.model.entity.MovieInterest;
import com.tassioauad.moviecheck.view.ListMovieInterestsView;

import java.util.List;

public class ListMovieInterestsPresenter {

    private ListMovieInterestsView view;
    private MovieInterestDao movieInterestDao;
    private UserDao userDao;

    public ListMovieInterestsPresenter(ListMovieInterestsView view, MovieInterestDao movieInterestDao, UserDao userDao) {
        this.view = view;
        this.movieInterestDao = movieInterestDao;
        this.userDao = userDao;
    }

    public void loadMovieInterests() {
        List<MovieInterest> movieInterestList = movieInterestDao.listAll(userDao.getLoggedUser());
        if(movieInterestList.size() == 0) {
            view.warnAnyInterestFounded();
        } else {
            view.showMovieInterests(movieInterestList);
        }
    }

    public void remove(MovieInterest movieInterest) {
        movieInterestDao.remove(movieInterest);
    }
}
