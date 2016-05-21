package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.dao.MovieWatchedDao;
import com.tassioauad.moviecheck.model.dao.UserDao;
import com.tassioauad.moviecheck.presenter.ListMovieWatchedPresenter;
import com.tassioauad.moviecheck.view.ListMovieWatchedView;
import com.tassioauad.moviecheck.view.fragment.ListMovieWatchedFragment;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {AppModule.class, ApiModule.class, DaoModule.class}, injects = ListMovieWatchedFragment.class)
public class ListMovieWatchedViewModule {

    private ListMovieWatchedView view;

    public ListMovieWatchedViewModule(ListMovieWatchedView view) {
        this.view = view;
    }

    @Provides
    public ListMovieWatchedPresenter provideListMovieWatchedPresenter(MovieWatchedDao movieWatchedDao, UserDao userDao) {
        return new ListMovieWatchedPresenter(view, movieWatchedDao, userDao);
    }
}
