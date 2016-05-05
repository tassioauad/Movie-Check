package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.api.VideoApi;
import com.tassioauad.moviecheck.presenter.ListMediaPresenter;
import com.tassioauad.moviecheck.view.ListMediaView;
import com.tassioauad.moviecheck.view.fragment.ListMediaFragment;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {AppModule.class, ApiModule.class}, injects = ListMediaFragment.class)
public class ListMediaViewModule {

    private ListMediaView view;

    public ListMediaViewModule(ListMediaView view) {
        this.view = view;
    }

    @Provides
    public ListMediaPresenter provideListVideoPresenter(VideoApi videoApi) {
        return new ListMediaPresenter(view, videoApi);
    }
}
