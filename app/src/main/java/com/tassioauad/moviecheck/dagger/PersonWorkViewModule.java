package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.api.CastApi;
import com.tassioauad.moviecheck.model.api.CrewApi;
import com.tassioauad.moviecheck.presenter.PersonWorkPresenter;
import com.tassioauad.moviecheck.view.PersonWorkView;
import com.tassioauad.moviecheck.view.fragment.PersonWorkFragment;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {AppModule.class, ApiModule.class}, injects = PersonWorkFragment.class)
public class PersonWorkViewModule {

    private PersonWorkView view;

    public PersonWorkViewModule(PersonWorkView view) {
        this.view = view;
    }

    @Provides
    public PersonWorkPresenter providePersonWorkPresenter(CastApi castApi, CrewApi crewApi) {
        return new PersonWorkPresenter(view, castApi, crewApi);
    }

}
