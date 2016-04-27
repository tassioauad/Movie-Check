package com.tassioauad.moviecheck.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tassioauad.moviecheck.MovieCheckApplication;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.dagger.HomeViewModule;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.presenter.HomePresenter;
import com.tassioauad.moviecheck.view.HomeView;
import com.tassioauad.moviecheck.view.adapter.NowPlayingMovieListAdapter;
import com.tassioauad.moviecheck.view.adapter.OnItemClickListener;
import com.tassioauad.moviecheck.view.adapter.PopularMovieListAdapter;
import com.tassioauad.moviecheck.view.adapter.TopRatedMovieListAdapter;
import com.tassioauad.moviecheck.view.adapter.UpcomingMovieListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeView {

    @Inject
    HomePresenter presenter;

    @Bind(R.id.recyclerview_nowplaying)
    RecyclerView recyclerViewNowPlaying;
    @Bind(R.id.recyclerview_popular)
    RecyclerView recyclerViewPopular;
    @Bind(R.id.recyclerview_toprated)
    RecyclerView recyclerViewTopRated;
    @Bind(R.id.recyclerview_upcoming)
    RecyclerView recyclerViewUpcoming;
    @Bind(R.id.progressbar_popular)
    ProgressBar progressBarPopular;
    @Bind(R.id.progressbar_toprated)
    ProgressBar progressBarTopRated;
    @Bind(R.id.progressbar_upcoming)
    ProgressBar progressBarUpcoming;
    @Bind(R.id.progressbar_nowplaying)
    ProgressBar progressBarNowPlaying;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.linearlayout_nowplaying_anyfounded)
    LinearLayout linearLayoutNowPlayingAnyFounded;
    @Bind(R.id.linearlayout_nowplaying_loadfailed)
    LinearLayout linearLayoutNowPlayingLoadFailed;
    @Bind(R.id.linearlayout_upcoming_anyfounded)
    LinearLayout linearLayoutUpcomingAnyFounded;
    @Bind(R.id.linearlayout_upcoming_loadfailed)
    LinearLayout linearLayoutUpcomingLoadFailed;
    @Bind(R.id.linearlayout_toprated_anyfounded)
    LinearLayout linearLayoutTopRatedAnyFounded;
    @Bind(R.id.linearlayout_toprated_loadfailed)
    LinearLayout linearLayoutTopRatedLoadFailed;
    @Bind(R.id.linearlayout_popular_anyfounded)
    LinearLayout linearLayoutPopularAnyFounded;
    @Bind(R.id.linearlayout_popular_loadfailed)
    LinearLayout linearLayoutPopularLoadFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        ((MovieCheckApplication) getApplication()).getObjectGraph().plus(new HomeViewModule(this)).inject(this);

        setSupportActionBar(toolbar);

        presenter.init();
    }

    @Override
    public void showLoadingUpcomingMovies() {
        progressBarUpcoming.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingUpcomingMovies() {
        progressBarUpcoming.setVisibility(View.GONE);
    }

    @Override
    public void showUpcomingMovies(List<Movie> movieList) {
        recyclerViewUpcoming.setVisibility(View.VISIBLE);
        linearLayoutUpcomingAnyFounded.setVisibility(View.GONE);
        linearLayoutUpcomingLoadFailed.setVisibility(View.GONE);
        recyclerViewUpcoming.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        recyclerViewUpcoming.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUpcoming.setAdapter(new UpcomingMovieListAdapter(movieList, new OnItemClickListener<Movie>() {
            @Override
            public void onClick(Movie movie) {

            }
        }));
    }

    @Override
    public void warnFailedToLoadUpcomingMovies() {
        recyclerViewUpcoming.setVisibility(View.GONE);
        linearLayoutUpcomingAnyFounded.setVisibility(View.GONE);
        linearLayoutUpcomingLoadFailed.setVisibility(View.VISIBLE);
    }

    @Override
    public void warnAnyUpcomingMovieFounded() {
        recyclerViewUpcoming.setVisibility(View.GONE);
        linearLayoutUpcomingAnyFounded.setVisibility(View.VISIBLE);
        linearLayoutUpcomingLoadFailed.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingPopularMovies() {
        progressBarPopular.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingPopularMovies() {
        progressBarPopular.setVisibility(View.GONE);

    }

    @Override
    public void warnAnyPopularMovieFounded() {
        recyclerViewPopular.setVisibility(View.GONE);
        linearLayoutPopularAnyFounded.setVisibility(View.VISIBLE);
        linearLayoutPopularLoadFailed.setVisibility(View.GONE);
    }

    @Override
    public void warnFailedToLoadPopularMovies() {
        recyclerViewPopular.setVisibility(View.GONE);
        linearLayoutPopularAnyFounded.setVisibility(View.GONE);
        linearLayoutPopularLoadFailed.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPopularMovies(List<Movie> movieList) {
        recyclerViewPopular.setVisibility(View.VISIBLE);
        linearLayoutPopularAnyFounded.setVisibility(View.GONE);
        linearLayoutPopularLoadFailed.setVisibility(View.GONE);
        recyclerViewPopular.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        recyclerViewPopular.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPopular.setAdapter(new PopularMovieListAdapter(movieList, new OnItemClickListener<Movie>() {
            @Override
            public void onClick(Movie movie) {

            }
        }));
    }

    @Override
    public void showLoadingTopRatedMovies() {
        progressBarTopRated.setVisibility(View.VISIBLE);
    }

    @Override
    public void warnAnyTopRatedMovieFounded() {
        recyclerViewTopRated.setVisibility(View.GONE);
        linearLayoutTopRatedAnyFounded.setVisibility(View.VISIBLE);
        linearLayoutTopRatedLoadFailed.setVisibility(View.GONE);
    }

    @Override
    public void showTopRatedMovies(List<Movie> movieList) {
        recyclerViewTopRated.setVisibility(View.VISIBLE);
        linearLayoutTopRatedAnyFounded.setVisibility(View.GONE);
        linearLayoutTopRatedLoadFailed.setVisibility(View.GONE);
        recyclerViewTopRated.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        recyclerViewTopRated.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTopRated.setAdapter(new TopRatedMovieListAdapter(movieList, new OnItemClickListener<Movie>() {
            @Override
            public void onClick(Movie movie) {

            }
        }));
    }

    @Override
    public void hideLoadingTopRatedMovies() {
        progressBarTopRated.setVisibility(View.GONE);
    }

    @Override
    public void warnFailedToLoadTopRatedMovies() {
        recyclerViewTopRated.setVisibility(View.GONE);
        linearLayoutTopRatedAnyFounded.setVisibility(View.GONE);
        linearLayoutTopRatedLoadFailed.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadingNowPlayingMovies() {
        progressBarNowPlaying.setVisibility(View.VISIBLE);
    }

    @Override
    public void warnAnyNowPlayingMovieFounded() {
        recyclerViewNowPlaying.setVisibility(View.GONE);
        linearLayoutNowPlayingAnyFounded.setVisibility(View.VISIBLE);
        linearLayoutNowPlayingLoadFailed.setVisibility(View.GONE);
    }

    @Override
    public void showNowPlayingMovies(List<Movie> movieList) {
        linearLayoutNowPlayingAnyFounded.setVisibility(View.GONE);
        linearLayoutNowPlayingLoadFailed.setVisibility(View.GONE);
        recyclerViewNowPlaying.setVisibility(View.VISIBLE);
        recyclerViewNowPlaying.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        recyclerViewNowPlaying.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNowPlaying.setAdapter(new NowPlayingMovieListAdapter(movieList, new OnItemClickListener<Movie>() {
            @Override
            public void onClick(Movie movie) {

            }
        }));
    }

    @Override
    public void hideLoadingNowPlayingMovies()    {
        progressBarNowPlaying.setVisibility(View.GONE);
    }

    @Override
    public void warnFailedToLoadNowPlayingMovies() {
        recyclerViewNowPlaying.setVisibility(View.GONE);
        linearLayoutNowPlayingAnyFounded.setVisibility(View.GONE);
        linearLayoutNowPlayingLoadFailed.setVisibility(View.VISIBLE);
    }

    public void morePopularMovies(View view) {
        startActivity(ListPopularMoviesActivity.newIntent(this));
    }

    public void moreNowPlayingMovies(View view) {

    }

    public void moreUpcomingMovies(View view) {

    }

    public void moreTopRatedMovies(View view) {
        startActivity(ListTopRatedMoviesActivity.newIntent(this));
    }
}
