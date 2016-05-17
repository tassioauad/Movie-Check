package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.dao.MovieInterestDao;
import com.tassioauad.moviecheck.model.dao.MovieWatchedDao;
import com.tassioauad.moviecheck.model.dao.UserDao;
import com.tassioauad.moviecheck.presenter.UserDetailPresenter;
import com.tassioauad.moviecheck.view.UserDetailView;
import com.tassioauad.moviecheck.view.fragment.UserDetailFragment;

import dagger.Module;
import dagger.Provides;

@Module(library = true, injects = UserDetailFragment.class, includes = {AppModule.class, ApiModule.class})
public class UserDetailViewModule {

    private UserDetailView view;

    public UserDetailViewModule(UserDetailView view) {
        this.view = view;
    }

    @Provides
    public UserDetailPresenter provideUserDetailPresenter(UserDao userDao, MovieInterestDao movieInterestDao, MovieWatchedDao movieWatchedDao) {
        return new UserDetailPresenter(view, userDao, movieInterestDao, movieWatchedDao, genreApi);
    }
}
