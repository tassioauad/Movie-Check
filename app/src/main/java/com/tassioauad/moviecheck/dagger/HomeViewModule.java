package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.model.dao.UserDao;
import com.tassioauad.moviecheck.presenter.HomePresenter;
import com.tassioauad.moviecheck.view.HomeView;
import com.tassioauad.moviecheck.view.activity.HomeActivity;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {ApiModule.class, DaoModule.class}, injects = HomeActivity.class)
public class HomeViewModule {

    HomeView view;

    public HomeViewModule(HomeView view) {
        this.view = view;
    }

    @Provides
    public HomePresenter provideHomePresenter(MovieApi movieApi, UserDao userDao) {
        return new HomePresenter(view, movieApi, userDao);
    }

}
