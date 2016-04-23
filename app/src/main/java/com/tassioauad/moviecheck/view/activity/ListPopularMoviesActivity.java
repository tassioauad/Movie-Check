package com.tassioauad.moviecheck.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tassioauad.moviecheck.MovieCheckApplication;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.dagger.ListPopularMoviesViewModule;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.presenter.ListPopularMoviesPresenter;
import com.tassioauad.moviecheck.view.ListPopularMoviesView;
import com.tassioauad.moviecheck.view.adapter.MovieListAdapter;
import com.tassioauad.moviecheck.view.adapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListPopularMoviesActivity extends AppCompatActivity implements ListPopularMoviesView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerview_movies)
    RecyclerView recyclerViewMovies;
    @Bind(R.id.progressbar)
    ProgressBar progressBar;
    @Bind(R.id.linearlayout_anyfounded)
    LinearLayout linearLayoutAnyFounded;
    @Bind(R.id.linearlayout_loadfailed)
    LinearLayout linearLayoutLoadFailed;

    @Inject
    ListPopularMoviesPresenter presenter;
    private List<Movie> movieList;
    private Integer page = 1;
    private Integer columns = 3;
    private static final String BUNDLE_KEY_MOVIELIST = "bundle_key_movielist";
    private static final String BUNDLE_KEY_PAGE = "bundle_key_page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listpopularmovies);
        ButterKnife.bind(this);
        ((MovieCheckApplication) getApplication()).getObjectGraph().plus(new ListPopularMoviesViewModule(this)).inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.listpopularmoviesactivity_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            presenter.loadMovies(page);
        } else {
            List<Movie> movieList = savedInstanceState.getParcelableArrayList(BUNDLE_KEY_MOVIELIST);
            page = savedInstanceState.getInt(BUNDLE_KEY_PAGE);
            showMovies(movieList);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (movieList != null) {
            outState.putParcelableArrayList(BUNDLE_KEY_MOVIELIST, new ArrayList<>(movieList));
        }
        outState.putInt(BUNDLE_KEY_PAGE, page);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoadingMovies() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void warnAnyMovieFounded() {
        if (movieList == null) {
            linearLayoutAnyFounded.setVisibility(View.VISIBLE);
            linearLayoutLoadFailed.setVisibility(View.GONE);
            recyclerViewMovies.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, R.string.listpopularmoviesactivity_anymoviefounded, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMovies(final List<Movie> newMovieList) {
        int scrollToItem = 0;
        if (movieList == null) {
            movieList = newMovieList;
        } else {
            scrollToItem = movieList.size() - columns;
            movieList.addAll(newMovieList);
        }
        linearLayoutAnyFounded.setVisibility(View.GONE);
        linearLayoutLoadFailed.setVisibility(View.GONE);
        recyclerViewMovies.setVisibility(View.VISIBLE);
        final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewMovies.setLayoutManager(staggeredGridLayoutManager);
        recyclerViewMovies.setItemAnimator(new DefaultItemAnimator());

        recyclerViewMovies.setAdapter(new MovieListAdapter(movieList, new OnItemClickListener<Movie>() {
            @Override
            public void onClick(Movie movie) {

            }
        }));
        recyclerViewMovies.scrollToPosition(scrollToItem);
        recyclerViewMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int count = movieList.size() - 1;

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int[] lastVisibleItemPositions = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
                    Arrays.sort(lastVisibleItemPositions);
                    if (lastVisibleItemPositions[lastVisibleItemPositions.length - 1] >= count) {
                        presenter.loadMovies(++page);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void hideLoadingMovies() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void warnFailedToLoadMovies() {
        if (movieList == null) {
            linearLayoutAnyFounded.setVisibility(View.GONE);
            linearLayoutLoadFailed.setVisibility(View.VISIBLE);
            recyclerViewMovies.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, R.string.listpopularmoviesactivity_failedtoloadmovie, Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ListPopularMoviesActivity.class);
    }
}
