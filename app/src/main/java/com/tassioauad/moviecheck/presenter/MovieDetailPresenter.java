package com.tassioauad.moviecheck.presenter;


import com.tassioauad.moviecheck.model.api.GenreApi;
import com.tassioauad.moviecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.moviecheck.model.entity.Genre;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.view.MovieDetailView;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailPresenter {

    private MovieDetailView view;
    private GenreApi genreApi;
    private Movie movie;

    public MovieDetailPresenter(MovieDetailView view, GenreApi genreApi) {
        this.view = view;
        this.genreApi = genreApi;
    }

    public void init(Movie movie) {
        this.movie = movie;
        view.showVoteCount(movie.getVoteCount());
        view.showVoteAverage(movie.getVoteAverage());
        view.showOverview(movie.getOverview());
        view.showReleaseDate(movie.getReleaseDate());
        view.showPoster(movie.getPosterUrl());
        view.showBackdrop(movie.getBackdropUrl());
        loadGenres();
    }

    private void loadGenres() {
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

}
