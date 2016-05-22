package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.api.ImageApi;
import com.tassioauad.moviecheck.presenter.ListPersonMediaPresenter;
import com.tassioauad.moviecheck.view.ListMovieMediaView;
import com.tassioauad.moviecheck.view.ListPersonMediaView;
import com.tassioauad.moviecheck.view.fragment.ListPersonMediaFragment;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {AppModule.class, ApiModule.class}, injects = ListPersonMediaFragment.class)
public class ListPersonMediaViewModule {

    private ListPersonMediaView view;

    public ListPersonMediaViewModule(ListPersonMediaView view) {
        this.view = view;
    }

    @Provides
    public ListPersonMediaPresenter provideListPersonMediaPresenter(ImageApi imageApi) {
        return new ListPersonMediaPresenter(view, imageApi);
    }
}
