package com.tassioauad.moviecheck.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tassioauad.moviecheck.MovieCheckApplication;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.dagger.ListMoviesByGenreViewModule;
import com.tassioauad.moviecheck.model.entity.Genre;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.presenter.ListMoviesByGenrePresenter;
import com.tassioauad.moviecheck.view.ListMoviesByGenreView;
import com.tassioauad.moviecheck.view.adapter.ListViewAdapterWithPagination;
import com.tassioauad.moviecheck.view.adapter.OnItemClickListener;
import com.tassioauad.moviecheck.view.adapter.OnShowMoreListener;
import com.tassioauad.moviecheck.view.adapter.MovieListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListMoviesByGenreActivity extends AppCompatActivity implements ListMoviesByGenreView {

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
    ListMoviesByGenrePresenter presenter;
    private List<Movie> movieList;
    private Integer columns = 3;
    private int scrollToItem;
    private int page = 1;
    private Genre genre;
    private static final String BUNDLE_KEY_MOVIELIST = "bundle_key_movielist";
    private static final String BUNDLE_KEY_PAGE = "bundle_key_page";
    private static final String KEY_GENRE = "GENRE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listmoviebygenre);
        ButterKnife.bind(this);
        ((MovieCheckApplication) getApplication()).getObjectGraph().plus(new ListMoviesByGenreViewModule(this)).inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            genre = getIntent().getParcelableExtra(KEY_GENRE);
            presenter.loadMovies(genre, page);
        } else {
            List<Movie> movieList = savedInstanceState.getParcelableArrayList(BUNDLE_KEY_MOVIELIST);
            page = savedInstanceState.getInt(BUNDLE_KEY_PAGE);
            showMovies(movieList);
        }

        getSupportActionBar().setTitle(genre.getName());
    }

    @Override
    protected void onStop() {
        presenter.stop();
        super.onStop();
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
        linearLayoutAnyFounded.setVisibility(View.VISIBLE);
        linearLayoutLoadFailed.setVisibility(View.GONE);
        recyclerViewMovies.setVisibility(View.GONE);
    }

    @Override
    public void showMovies(final List<Movie> newMovieList) {
        if (movieList == null) {
            movieList = newMovieList;
        } else {
            movieList.addAll(newMovieList);
        }
        linearLayoutAnyFounded.setVisibility(View.GONE);
        linearLayoutLoadFailed.setVisibility(View.GONE);
        recyclerViewMovies.setVisibility(View.VISIBLE);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, columns, GridLayoutManager.VERTICAL, false);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position >= movieList.size() ? columns : 1;
            }
        });
        recyclerViewMovies.setLayoutManager(layoutManager);
        recyclerViewMovies.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMovies.setAdapter(
                new ListViewAdapterWithPagination(
                        new MovieListAdapter(movieList, new OnItemClickListener<Movie>() {
                            @Override
                            public void onClick(Movie movie) {
                                startActivity(MovieProfileActivity.newIntent(ListMoviesByGenreActivity.this, movie));
                            }
                        }
                        ),
                        new OnShowMoreListener() {
                            @Override
                            public void showMore() {
                                scrollToItem = layoutManager.findFirstVisibleItemPosition();
                                presenter.loadMovies(genre, ++page);
                            }
                        }
                )
        );
        recyclerViewMovies.scrollToPosition(scrollToItem);
    }

    @Override
    public void hideLoadingMovies() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void warnFailedToLoadMovies() {
        linearLayoutAnyFounded.setVisibility(View.GONE);
        linearLayoutLoadFailed.setVisibility(View.VISIBLE);
        recyclerViewMovies.setVisibility(View.GONE);
    }

    public static Intent newIntent(Context context, Genre genre) {
        Intent intent = new Intent(context, ListMoviesByGenreActivity.class);
        intent.putExtra(KEY_GENRE, genre);

        return intent;
    }
}
