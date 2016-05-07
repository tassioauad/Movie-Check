package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.api.PersonApi;
import com.tassioauad.moviecheck.presenter.SearchPersonPresenter;
import com.tassioauad.moviecheck.view.SearchPersonView;
import com.tassioauad.moviecheck.view.activity.SearchPersonActivity;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = ApiModule.class, injects = SearchPersonActivity.class)
public class SearchPersonViewModule {

    SearchPersonView view;

    public SearchPersonViewModule(SearchPersonView view) {
        this.view = view;
    }

    @Provides
    public SearchPersonPresenter provideSearchPersonPresenter(PersonApi movieApi) {
        return new SearchPersonPresenter(view, movieApi);
    }
}
