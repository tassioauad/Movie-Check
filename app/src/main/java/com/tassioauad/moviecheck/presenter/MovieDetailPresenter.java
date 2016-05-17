package com.tassioauad.moviecheck.presenter;


import com.tassioauad.moviecheck.model.api.GenreApi;
import com.tassioauad.moviecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.moviecheck.model.dao.MovieInterestDao;
import com.tassioauad.moviecheck.model.dao.MovieWatchedDao;
import com.tassioauad.moviecheck.model.dao.UserDao;
import com.tassioauad.moviecheck.model.entity.Genre;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.MovieInterest;
import com.tassioauad.moviecheck.model.entity.MovieWatched;
import com.tassioauad.moviecheck.view.MovieDetailView;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailPresenter {

    private MovieDetailView view;
    private GenreApi genreApi;
    private MovieInterestDao movieInterestDao;
    private MovieWatchedDao movieWatchedDao;
    private UserDao userDao;
    private Movie movie;
    private MovieInterest movieInterest;
    private MovieWatched movieWatched;

    public MovieDetailPresenter(MovieDetailView view, GenreApi genreApi, MovieInterestDao movieInterestDao, MovieWatchedDao movieWatchedDao, UserDao userDao) {
        this.view = view;
        this.genreApi = genreApi;
        this.movieInterestDao = movieInterestDao;
        this.movieWatchedDao = movieWatchedDao;
        this.userDao = userDao;
    }

    public void init(Movie movie) {
        this.movie = movie;
        view.showVoteCount(movie.getVoteCount());
        view.showVoteAverage(movie.getVoteAverage());
        view.showOverview(movie.getOverview());
        view.showReleaseDate(movie.getReleaseDate());
        view.showPoster(movie.getPosterUrl());
        view.showBackdrop(movie.getBackdropUrl());

        movieWatched = movieWatchedDao.findByMovie(movie, userDao.getLoggedUser());
        if (userDao.getLoggedUser() == null || movieWatched != null) {
            view.disableToCheckInterest();
            if (movieWatched != null) {
                view.showUserClassification(movieWatched.getVote());
            }
        } else {
            view.enableToCheckInterest();
            movieInterest = movieInterestDao.findByMovie(movie, userDao.getLoggedUser());
            if (movieInterest != null) {
                view.checkInterest();
            }
        }
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
                        if (movie.getGenreId().contains(genre.getId())) {
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

    public void stop() {
        genreApi.cancelAllServices();
    }

    public void checkInterest() {
        if (movieInterest == null) {
            movieInterest = new MovieInterest();
            movieInterest.setMovie(movie);
            movieInterest.setUser(userDao.getLoggedUser());

            movieInterestDao.insert(movieInterest);
            view.checkInterest();
        } else {
            movieInterestDao.remove(movieInterest);
            movieInterest = null;
            view.uncheckInterest();
        }
    }

    public void informUserClassification(float rating) {
        if(movieWatched == null) {
            movieWatched = new MovieWatched();
            movieWatched.setMovie(movie);
            movieWatched.setUser(userDao.getLoggedUser());
        }
        movieWatched.setVote(rating);
        movieWatchedDao.save(movieWatched);
        movieInterestDao.remove(movieWatched.getMovie(), userDao.getLoggedUser());
        view.disableToCheckInterest();
        view.showUserClassification(rating);
    }
}
