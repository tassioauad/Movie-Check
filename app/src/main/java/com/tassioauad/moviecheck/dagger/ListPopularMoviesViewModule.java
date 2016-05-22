package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.presenter.ListPopularMoviesPresenter;
import com.tassioauad.moviecheck.view.ListPopularMoviesView;
import com.tassioauad.moviecheck.view.activity.ListPopularMoviesActivity;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = ApiModule.class, injects = ListPopularMoviesActivity.class)
public class ListPopularMoviesViewModule {

    ListPopularMoviesView view;

    public ListPopularMoviesViewModule(ListPopularMoviesView view) {
        this.view = view;
    }

    @Provides
    public ListPopularMoviesPresenter provideListPopularMoviesPresenter(MovieApi movieApi) {
        return new ListPopularMoviesPresenter(view, movieApi);
    }
}
