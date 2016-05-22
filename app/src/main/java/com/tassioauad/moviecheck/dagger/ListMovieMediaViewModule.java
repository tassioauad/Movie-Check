package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.api.ImageApi;
import com.tassioauad.moviecheck.model.api.VideoApi;
import com.tassioauad.moviecheck.presenter.ListMovieMediaPresenter;
import com.tassioauad.moviecheck.view.ListMovieMediaView;
import com.tassioauad.moviecheck.view.fragment.ListMovieMediaFragment;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {AppModule.class, ApiModule.class}, injects = ListMovieMediaFragment.class)
public class ListMovieMediaViewModule {

    private ListMovieMediaView view;

    public ListMovieMediaViewModule(ListMovieMediaView view) {
        this.view = view;
    }

    @Provides
    public ListMovieMediaPresenter provideListVideoPresenter(VideoApi videoApi, ImageApi imageApi) {
        return new ListMovieMediaPresenter(view, videoApi, imageApi);
    }
}
