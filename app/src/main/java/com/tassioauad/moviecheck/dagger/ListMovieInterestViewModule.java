package com.tassioauad.moviecheck.dagger;

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

    public ListMovieInterestViewModule(ListMovieInterestsView view) {
        this.view = view;
    }

    @Provides
    public ListMovieInterestsPresenter provideListMovieInterestsPresenter(MovieInterestDao movieInterestDao, UserDao userDao) {
        return new ListMovieInterestsPresenter(view, movieInterestDao, userDao);
    }
}
