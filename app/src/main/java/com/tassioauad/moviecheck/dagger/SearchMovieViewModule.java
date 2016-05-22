package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.presenter.SearchMoviePresenter;
import com.tassioauad.moviecheck.view.SearchMovieView;
import com.tassioauad.moviecheck.view.activity.SearchMovieActivity;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = ApiModule.class, injects = SearchMovieActivity.class)
public class SearchMovieViewModule {

    SearchMovieView view;

    public SearchMovieViewModule(SearchMovieView view) {
        this.view = view;
    }

    @Provides
    public SearchMoviePresenter provideSearchMoviePresenter(MovieApi movieApi) {
        return new SearchMoviePresenter(view, movieApi);
    }
}
