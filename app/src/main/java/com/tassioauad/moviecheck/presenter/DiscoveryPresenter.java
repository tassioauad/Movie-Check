package com.tassioauad.moviecheck.presenter;

import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.moviecheck.model.dao.MovieInterestDao;
import com.tassioauad.moviecheck.model.dao.MovieNotInterestDao;
import com.tassioauad.moviecheck.model.dao.MovieWatchedDao;
import com.tassioauad.moviecheck.model.dao.UserDao;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.MovieInterest;
import com.tassioauad.moviecheck.model.entity.MovieNotInterest;
import com.tassioauad.moviecheck.view.DiscoveryView;

import java.util.List;

public class DiscoveryPresenter {

    private DiscoveryView view;
    private MovieApi movieApi;
    private MovieWatchedDao movieWatchedDao;
    private MovieInterestDao movieInterestDao;
    private MovieNotInterestDao movieNotInterestDao;
    private UserDao userDao;
    private List<Movie> movieList;
    private boolean loadingMovies;
    private int page;

    public DiscoveryPresenter(DiscoveryView view, MovieApi movieApi, MovieWatchedDao movieWatchedDao,
                              MovieInterestDao movieInterestDao, MovieNotInterestDao movieNotInterestDao, UserDao userDao) {
        this.view = view;
        this.movieApi = movieApi;
        this.movieWatchedDao = movieWatchedDao;
        this.movieInterestDao = movieInterestDao;
        this.movieNotInterestDao = movieNotInterestDao;
        this.userDao = userDao;
    }

    public void init(List<Movie> movieList, int page, int index) {
        this.movieList = movieList;
        this.page = page;
        loadMovie(index);
    }

    public void loadAllMoviesFromPage(final int page) {
        this.page = page;
        loadingMovies = true;
        view.showLoading();
        movieApi.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                movieList = (List<Movie>) object;
                if(movieList != null && movieList.size() > 0) {
                    view.movieLoaded(movieList, page);
                } else {
                    view.warnWasNotPossibleToLoadMoreMovies();
                }
                view.hideLoading();
                loadingMovies = false;
            }

            @Override
            public void onException(Exception exception) {
                view.warnFailedToLoadMoreMovies();
                view.hideLoading();
                loadingMovies = false;
            }
        });
        movieApi.discoverMovies(page);
    }

    public void loadMovie(int index) {
        if(index != 0 && movieWatchedDao.findByMovie(movieList.get(index-1), userDao.getLoggedUser()) == null &&
                movieInterestDao.findByMovie(movieList.get(index-1), userDao.getLoggedUser()) == null) {
            MovieNotInterest movieNotInterest = new MovieNotInterest();
            movieNotInterest.setMovie(movieList.get(index-1));
            movieNotInterest.setUser(userDao.getLoggedUser());
            movieNotInterestDao.insert(movieNotInterest);
        }

        if (movieList.size() <= index) {
            if(!loadingMovies) {
                loadAllMoviesFromPage(++page);
            }
        } else {
            while(movieWatchedDao.findByMovie(movieList.get(index), userDao.getLoggedUser()) != null ||
                    movieInterestDao.findByMovie(movieList.get(index), userDao.getLoggedUser()) != null ||
                    movieNotInterestDao.findByMovie(movieList.get(index), userDao.getLoggedUser()) != null) {
                index++;
                if(movieList.size() == index) {
                    loadAllMoviesFromPage(++page);
                    return;
                }
            }
            view.showMovie(movieList.get(index), index);
        }
    }

    public void stop() {
        movieApi.cancelAllServices();
    }

    public void checkInterest(int index) {
        MovieInterest movieInterest = movieInterestDao.findByMovie(movieList.get(index), userDao.getLoggedUser());
        if (movieInterest == null) {
            movieInterest = new MovieInterest();
            movieInterest.setMovie(movieList.get(index));
            movieInterest.setUser(userDao.getLoggedUser());

            movieInterestDao.insert(movieInterest);
            view.checkInterest();
        } else {
            movieInterestDao.remove(movieInterest);
            view.uncheckInterest();
        }
    }
}
