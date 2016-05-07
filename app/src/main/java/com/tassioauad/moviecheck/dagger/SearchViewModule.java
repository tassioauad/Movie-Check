package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.model.api.PersonApi;
import com.tassioauad.moviecheck.presenter.SearchPresenter;
import com.tassioauad.moviecheck.view.SearchView;
import com.tassioauad.moviecheck.view.activity.SearchActivity;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {AppModule.class, ApiModule.class}, injects = SearchActivity.class)
public class SearchViewModule {

    private SearchView view;

    public SearchViewModule(SearchView view) {
        this.view = view;
    }

    @Provides
    public SearchPresenter provideSearchPresenter(MovieApi movieApi, PersonApi personApi) {
        return new SearchPresenter(view, movieApi, personApi);
    }

}
