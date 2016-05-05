package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.presenter.MovieProfilePresenter;
import com.tassioauad.moviecheck.view.MovieProfileView;
import com.tassioauad.moviecheck.view.activity.MovieProfileActivity;

import dagger.Module;
import dagger.Provides;

@Module(library = true, injects = {MovieProfileActivity.class}, includes = {AppModule.class, ApiModule.class})
public class MovieProfileViewModule {

    private MovieProfileView view;

    public MovieProfileViewModule(MovieProfileView view) {
        this.view = view;
    }

    @Provides
    public MovieProfilePresenter provideProfilePresenter() {
        return new MovieProfilePresenter(view);
    }
}
