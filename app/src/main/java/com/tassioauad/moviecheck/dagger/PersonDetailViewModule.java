package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.api.PersonApi;
import com.tassioauad.moviecheck.presenter.PersonDetailPresenter;
import com.tassioauad.moviecheck.view.PersonDetailView;
import com.tassioauad.moviecheck.view.fragment.PersonDetailFragment;

import dagger.Module;
import dagger.Provides;

@Module(library = true, injects = PersonDetailFragment.class, includes = {AppModule.class, ApiModule.class})
public class PersonDetailViewModule {

    private PersonDetailView view;

    public PersonDetailViewModule(PersonDetailView view) {
        this.view = view;
    }

    @Provides
    public PersonDetailPresenter providePersonDetailPresenter(PersonApi personApi) {
        return new PersonDetailPresenter(view, personApi);
    }
}
