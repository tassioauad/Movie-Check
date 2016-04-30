package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.presenter.ListPopularMoviesPresenter;
import com.tassioauad.moviecheck.presenter.ListTopRatedMoviesPresenter;
import com.tassioauad.moviecheck.view.ListPopularMoviesView;
import com.tassioauad.moviecheck.view.ListTopRatedMoviesView;
import com.tassioauad.moviecheck.view.activity.ListTopRatedMoviesActivity;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = ApiModule.class, injects = ListTopRatedMoviesActivity.class)
public class ListTopRatedMoviesViewModule {

    ListTopRatedMoviesView view;

    public ListTopRatedMoviesViewModule(ListTopRatedMoviesView view) {
        this.view = view;
    }

    @Provides
    public ListTopRatedMoviesPresenter provideListTopRatedMoviesPresenter(MovieApi movieApi) {
        return new ListTopRatedMoviesPresenter(view, movieApi);
    }
}
