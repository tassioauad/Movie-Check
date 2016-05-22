package com.tassioauad.moviecheck.presenter;

import com.tassioauad.moviecheck.model.dao.DaoListener;
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
        movieInterestDao.setDaoListener(new DaoListener() {
            @Override
            public void onLoad(Object object) {
                List<MovieInterest> movieInterestList = (List<MovieInterest>) object;
                if(movieInterestList.size() == 0) {
                    view.warnAnyInterestFounded();
                } else {
                    view.showMovieInterests(movieInterestList);
                }
            }
        });
        movieInterestDao.listAll(userDao.getLoggedUser());
    }

    public void remove(MovieInterest movieInterest) {
        movieInterestDao.remove(movieInterest);
        view.warnMovieRemoved(movieInterest.getMovie());
    }
}
