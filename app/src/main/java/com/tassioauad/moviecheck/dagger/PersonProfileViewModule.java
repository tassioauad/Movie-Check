package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.presenter.PersonProfilePresenter;
import com.tassioauad.moviecheck.view.PersonProfileView;
import com.tassioauad.moviecheck.view.activity.PersonProfileActivity;

import dagger.Module;
import dagger.Provides;

@Module(library = true, injects = {PersonProfileActivity.class}, includes = {AppModule.class, ApiModule.class})
public class PersonProfileViewModule {

    private PersonProfileView view;

    public PersonProfileViewModule(PersonProfileView view) {
        this.view = view;
    }

    @Provides
    public PersonProfilePresenter providePersonProfilePresenter() {
        return new PersonProfilePresenter(view);
    }
}
