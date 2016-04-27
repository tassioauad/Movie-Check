package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.presenter.ListUpcomingMoviesPresenter;
import com.tassioauad.moviecheck.view.ListUpcomingMoviesView;
import com.tassioauad.moviecheck.view.activity.ListUpcomingMoviesActivity;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = ApiModule.class, injects = ListUpcomingMoviesActivity.class)
public class ListUpcomingMoviesViewModule {

    ListUpcomingMoviesView view;

    public ListUpcomingMoviesViewModule(ListUpcomingMoviesView view) {
        this.view = view;
    }

    @Provides
    public ListUpcomingMoviesPresenter provideListUpcomingMoviesPresenter(MovieApi movieApi) {
        return new ListUpcomingMoviesPresenter(view, movieApi);
    }
}
