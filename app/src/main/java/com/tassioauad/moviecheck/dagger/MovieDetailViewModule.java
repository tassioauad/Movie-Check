package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.presenter.MovieDetailPresenter;
import com.tassioauad.moviecheck.view.fragment.MovieDetailFragment;
import com.tassioauad.moviecheck.view.MovieDetailView;

import dagger.Module;
import dagger.Provides;

@Module(library = true, injects = MovieDetailFragment.class, includes = {AppModule.class, ApiModule.class})
public class MovieDetailViewModule {

    private MovieDetailView view;

    public MovieDetailViewModule(MovieDetailView view) {
        this.view = view;
    }

    @Provides
    public MovieDetailPresenter provideMovieDetailPresenter() {
        return new MovieDetailPresenter(view);
    }
}
