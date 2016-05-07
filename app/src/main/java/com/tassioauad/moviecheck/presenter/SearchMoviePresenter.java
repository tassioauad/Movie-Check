package com.tassioauad.moviecheck.presenter;

import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.view.ListUpcomingMoviesView;
import com.tassioauad.moviecheck.view.SearchMovieView;

import java.util.List;

public class SearchMoviePresenter {

    private SearchMovieView view;
    private MovieApi movieApi;

    public SearchMoviePresenter(SearchMovieView view, MovieApi movieApi) {
        this.view = view;
        this.movieApi = movieApi;
    }

    public void search(String query, Integer page) {
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

        movieApi.listByName(query, page);
    }


    public void stop() {
        movieApi.cancelAllServices();
    }
}
