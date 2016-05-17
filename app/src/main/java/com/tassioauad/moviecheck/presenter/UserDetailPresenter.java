package com.tassioauad.moviecheck.presenter;


import com.tassioauad.moviecheck.model.api.GenreApi;
import com.tassioauad.moviecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.moviecheck.model.dao.MovieInterestDao;
import com.tassioauad.moviecheck.model.dao.MovieWatchedDao;
import com.tassioauad.moviecheck.model.dao.UserDao;
import com.tassioauad.moviecheck.model.entity.Genre;
import com.tassioauad.moviecheck.model.entity.User;
import com.tassioauad.moviecheck.view.UserDetailView;

import java.util.ArrayList;
import java.util.List;

public class UserDetailPresenter {

    private UserDetailView view;
    private UserDao userDao;
    private MovieInterestDao movieInterestDao;
    private MovieWatchedDao movieWatchedDao;
    private GenreApi genreApi;
    private User loggedUser;

    public UserDetailPresenter(UserDetailView view, UserDao userDao, MovieInterestDao movieInterestDao, MovieWatchedDao movieWatchedDao, GenreApi genreApi) {
        this.view = view;
        this.userDao = userDao;
        this.movieInterestDao = movieInterestDao;
        this.movieWatchedDao = movieWatchedDao;
        this.genreApi = genreApi;
    }

    public void init() {
        loggedUser = userDao.getLoggedUser();
        view.showUser(loggedUser);
        view.showUpcomingInterests(movieInterestDao.listAllUpcoming(loggedUser));
    }

    public void loadGenres() {
        view.showLoadingGenres();
        genreApi.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Genre> genreList = (List<Genre>) object;
                if (genreList == null || genreList.size() == 0) {
                    view.warnAnyGenreFounded();
                } else {
                    List<Genre> genresOfTheMovieList = new ArrayList<>();
                    for (Genre genre : genreList) {
                        if (movieWatchedDao.favoriteGenres(loggedUser).contains(genre.getId())) {
                            genresOfTheMovieList.add(genre);
                        }
                    }
                    view.showGenres(genresOfTheMovieList);
                }
                view.hideLoadingGenres();
            }

            @Override
            public void onException(Exception exception) {
                view.warnFailedOnLoadGenres();
                view.hideLoadingGenres();
            }
        });
        genreApi.listAllOfMovie();
    }

}
