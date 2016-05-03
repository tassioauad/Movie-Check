package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.presenter.ListMoviesByGenrePresenter;
import com.tassioauad.moviecheck.view.ListMoviesByGenreView;
import com.tassioauad.moviecheck.view.activity.ListMoviesByGenreActivity;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = ApiModule.class, injects = ListMoviesByGenreActivity.class)
public class ListMoviesByGenreViewModule {

    ListMoviesByGenreView view;

    public ListMoviesByGenreViewModule(ListMoviesByGenreView view) {
        this.view = view;
    }

    @Provides
    public ListMoviesByGenrePresenter provideListMoviesByGenrePresenter(MovieApi movieApi) {
        return new ListMoviesByGenrePresenter(view, movieApi);
    }
}
