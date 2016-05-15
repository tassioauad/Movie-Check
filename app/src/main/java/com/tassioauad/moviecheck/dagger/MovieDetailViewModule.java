package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.api.GenreApi;
import com.tassioauad.moviecheck.model.dao.MovieInterestDao;
import com.tassioauad.moviecheck.model.dao.UserDao;
import com.tassioauad.moviecheck.presenter.MovieDetailPresenter;
import com.tassioauad.moviecheck.view.MovieDetailView;
import com.tassioauad.moviecheck.view.fragment.MovieDetailFragment;

import dagger.Module;
import dagger.Provides;

@Module(library = true, injects = MovieDetailFragment.class, includes = {AppModule.class, ApiModule.class, DaoModule.class})
public class MovieDetailViewModule {

    private MovieDetailView view;

    public MovieDetailViewModule(MovieDetailView view) {
        this.view = view;
    }

    @Provides
    public MovieDetailPresenter provideMovieDetailPresenter(GenreApi genreApi, MovieInterestDao movieInterestDao, UserDao userDao) {
        return new MovieDetailPresenter(view, genreApi, movieInterestDao, userDao);
    }
}
