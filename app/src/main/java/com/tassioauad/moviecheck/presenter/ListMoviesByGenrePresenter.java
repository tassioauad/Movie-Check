package com.tassioauad.moviecheck.presenter;

import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.moviecheck.model.entity.Genre;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.view.ListMoviesByGenreView;
import com.tassioauad.moviecheck.view.ListPopularMoviesView;

import java.util.List;

public class ListMoviesByGenrePresenter {

    private ListMoviesByGenreView view;
    private MovieApi movieApi;

    public ListMoviesByGenrePresenter(ListMoviesByGenreView view, MovieApi movieApi) {
        this.view = view;
        this.movieApi = movieApi;
    }

    public void loadMovies(Genre genre, Integer page) {
        view.showLoadingMovies();
        movieApi.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Movie> movieList = (List<Movie>) object;
                if (movieList == null || movieList.size() == 0) {
                    view.warnAnyMovieFounded();
                } else {
                    view.showMovies(movieList);
                }
                view.hideLoadingMovies();
            }

            @Override
            public void onException(Exception exception) {
                view.warnFailedToLoadMovies();
                view.hideLoadingMovies();
            }
        });

        movieApi.listByGenre(genre, page);
    }


}
