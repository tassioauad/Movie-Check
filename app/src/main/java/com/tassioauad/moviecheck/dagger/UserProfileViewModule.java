package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.dao.UserDao;
import com.tassioauad.moviecheck.presenter.UserPerfilPresenter;
import com.tassioauad.moviecheck.view.UserProfileView;
import com.tassioauad.moviecheck.view.activity.UserProfileActivity;

import dagger.Module;
import dagger.Provides;

@Module(library = true, injects = {UserProfileActivity.class}, includes = {AppModule.class, ApiModule.class, DaoModule.class})
public class UserProfileViewModule {

    private UserProfileView view;

    public UserProfileViewModule(UserProfileView view) {
        this.view = view;
    }

    @Provides
    public UserPerfilPresenter provideUserPresenter(UserDao userDao) {
        return new UserPerfilPresenter(view, userDao);
    }
}
