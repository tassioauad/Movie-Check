package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.presenter.ListNowPlayingMoviesPresenter;
import com.tassioauad.moviecheck.view.ListNowPlayingMoviesView;
import com.tassioauad.moviecheck.view.activity.ListNowPlayingMoviesActivity;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = ApiModule.class, injects = ListNowPlayingMoviesActivity.class)
public class ListNowPlayingMoviesViewModule {

    ListNowPlayingMoviesView view;

    public ListNowPlayingMoviesViewModule(ListNowPlayingMoviesView view) {
        this.view = view;
    }

    @Provides
    public ListNowPlayingMoviesPresenter provideListNowPlayingMoviesPresenter(MovieApi movieApi) {
        return new ListNowPlayingMoviesPresenter(view, movieApi);
    }
}
