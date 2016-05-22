package com.tassioauad.moviecheck.dagger;

import android.support.v4.app.FragmentActivity;

import com.tassioauad.moviecheck.model.dao.MovieInterestDao;
import com.tassioauad.moviecheck.model.dao.UserDao;
import com.tassioauad.moviecheck.presenter.ListMovieInterestsPresenter;
import com.tassioauad.moviecheck.view.ListMovieInterestsView;
import com.tassioauad.moviecheck.view.fragment.ListMovieInterestsFragment;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {AppModule.class, ApiModule.class, DaoModule.class}, injects = ListMovieInterestsFragment.class)
public class ListMovieInterestViewModule {

    private ListMovieInterestsView view;
    private FragmentActivity activity;

    public ListMovieInterestViewModule(ListMovieInterestsView view, FragmentActivity activity) {
        this.view = view;
        this.activity = activity;
    }

    @Provides
    public ListMovieInterestsPresenter provideListMovieInterestsPresenter(MovieInterestDao movieInterestDao, UserDao userDao) {
        movieInterestDao.setActivity(activity);
        return new ListMovieInterestsPresenter(view, movieInterestDao, userDao);
    }
}
