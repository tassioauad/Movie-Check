package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.api.CastApi;
import com.tassioauad.moviecheck.model.api.CrewApi;
import com.tassioauad.moviecheck.presenter.CastCrewPresenter;
import com.tassioauad.moviecheck.view.CastCrewView;
import com.tassioauad.moviecheck.view.fragment.CastCrewFragment;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {AppModule.class, ApiModule.class}, injects = CastCrewFragment.class)
public class CastCrewViewModule {

    private CastCrewView view;

    public CastCrewViewModule(CastCrewView view) {
        this.view = view;
    }

    @Provides
    public CastCrewPresenter provideCastCrewPresenter(CastApi castApi, CrewApi crewApi) {
        return new CastCrewPresenter(view, castApi, crewApi);
    }

}
