package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.model.dao.MovieInterestDao;
import com.tassioauad.moviecheck.model.dao.MovieWatchedDao;
import com.tassioauad.moviecheck.model.dao.UserDao;
import com.tassioauad.moviecheck.presenter.DiscoveryPresenter;
import com.tassioauad.moviecheck.view.DiscoveryView;
import com.tassioauad.moviecheck.view.activity.DiscoveryActivity;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {ApiModule.class, DaoModule.class}, injects = DiscoveryActivity.class)
public class DiscoveryViewModule {

    private DiscoveryView view;

    public DiscoveryViewModule(DiscoveryView view) {
        this.view = view;
    }

    @Provides
    public DiscoveryPresenter provideDiscoveryPresenter(MovieApi movieApi, MovieWatchedDao movieWatchedDao,
                                                        MovieInterestDao movieInterestDao, UserDao userDao) {
        return new DiscoveryPresenter(view, movieApi, movieWatchedDao, movieInterestDao, userDao);
    }
}
