package com.tassioauad.moviecheck.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tassioauad.moviecheck.MovieCheckApplication;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.dagger.SearchViewModule;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.Person;
import com.tassioauad.moviecheck.presenter.SearchPresenter;
import com.tassioauad.moviecheck.view.adapter.MovieListAdapter;
import com.tassioauad.moviecheck.view.adapter.OnItemClickListener;
import com.tassioauad.moviecheck.view.adapter.PersonListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements com.tassioauad.moviecheck.view.SearchView {

    @Inject
    SearchPresenter presenter;

    private List<Movie> movieList;
    private List<Person> personList;
    private String query;
    private static final String KEY_PERSONLIST = "PERSONLIST";
    private static final String KEY_MOVIELIST = "MOVIELIST";
    private static final String KEY_QUERY = "QUERY";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerview_movie)
    RecyclerView recyclerViewMovie;
    @Bind(R.id.recyclerview_person)
    RecyclerView recyclerViewPerson;
    @Bind(R.id.linearlayout_movie_anyfounded)
    LinearLayout linearLayoutAnyMovieFounded;
    @Bind(R.id.linearlayout_movie_loadfailed)
    LinearLayout linearLayoutMovieLoadFailed;
    @Bind(R.id.progressbar_movie)
    ProgressBar progressBarMovie;
    @Bind(R.id.linearlayout_person_anyfounded)
    LinearLayout linearLayoutAnyPersonFounded;
    @Bind(R.id.linearlayout_person_loadfailed)
    LinearLayout linearLayoutPersonLoadFailed;
    @Bind(R.id.progressbar_person)
    ProgressBar progressBarPerson;
    @Bind(R.id.textview_moremovie)
    TextView textViewMoreMovies;
    @Bind(R.id.textview_moreperson)
    TextView textViewMorePerson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ((MovieCheckApplication) getApplication()).getObjectGraph().plus(new SearchViewModule(this)).inject(this);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        query = getIntent().getStringExtra(KEY_QUERY);
        getSupportActionBar().setTitle(query);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            personList = savedInstanceState.getParcelableArrayList(KEY_PERSONLIST);
            movieList = savedInstanceState.getParcelableArrayList(KEY_MOVIELIST);
            if (movieList != null) {
                showMovies(movieList);
            }
            if (personList != null) {
                showPerson(personList);
            }
        }
    }


    @Override
    public void onResume() {
        if (movieList == null) {
            if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
                presenter.searchMovies(getIntent().getStringExtra(SearchManager.QUERY));
            } else {
                presenter.searchMovies(query);
            }
        } else {
            showMovies(movieList);
        }
        if (personList == null) {
            if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
                presenter.searchPerson(getIntent().getStringExtra(SearchManager.QUERY));
            } else {
                presenter.searchPerson(query);
            }
        } else {
            showPerson(personList);
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchView =
                (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        presenter.stop();
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (personList != null) {
            outState.putParcelableArrayList(KEY_PERSONLIST, new ArrayList<>(personList));
        }
        if (movieList != null) {
            outState.putParcelableArrayList(KEY_MOVIELIST, new ArrayList<>(movieList));
        }
    }

    public static Intent newInstance(Context context, String query) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(KEY_QUERY, query);
        return intent;
    }

    @Override
    public void hideLoadingPerson() {
        progressBarPerson.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingPerson() {
        progressBarPerson.setVisibility(View.VISIBLE);
        linearLayoutPersonLoadFailed.setVisibility(View.GONE);
        linearLayoutAnyPersonFounded.setVisibility(View.GONE);
    }

    @Override
    public void showPerson(List<Person> personList) {
        this.personList = personList;
        if(personList.size() == 20) {
            textViewMorePerson.setVisibility(View.VISIBLE);
        }
        linearLayoutPersonLoadFailed.setVisibility(View.GONE);
        linearLayoutAnyPersonFounded.setVisibility(View.GONE);
        recyclerViewPerson.setVisibility(View.VISIBLE);
        recyclerViewPerson.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        recyclerViewPerson.setAdapter(new PersonListAdapter(personList, new OnItemClickListener<Person>() {
            @Override
            public void onClick(Person person) {
                startActivity(PersonProfileActivity.newIntent(SearchActivity.this, person));
            }
        }));
    }

    @Override
    public void warnAnyPersonFounded() {
        linearLayoutPersonLoadFailed.setVisibility(View.GONE);
        linearLayoutAnyPersonFounded.setVisibility(View.VISIBLE);
        recyclerViewPerson.setVisibility(View.GONE);
    }

    @Override
    public void warnFailedToLoadPerson() {
        linearLayoutPersonLoadFailed.setVisibility(View.VISIBLE);
        linearLayoutPersonLoadFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.searchPerson(query);
            }
        });
        linearLayoutAnyPersonFounded.setVisibility(View.GONE);
        recyclerViewPerson.setVisibility(View.GONE);
    }

    @Override
    public void hideLoadingMovies() {
        progressBarMovie.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingMovies() {
        progressBarMovie.setVisibility(View.VISIBLE);
        linearLayoutMovieLoadFailed.setVisibility(View.GONE);
        linearLayoutAnyMovieFounded.setVisibility(View.GONE);
    }

    @Override
    public void showMovies(List<Movie> movieList) {
        this.movieList = movieList;
        if(movieList.size() == 20) {
            textViewMoreMovies.setVisibility(View.VISIBLE);
        }
        linearLayoutMovieLoadFailed.setVisibility(View.GONE);
        linearLayoutAnyMovieFounded.setVisibility(View.GONE);
        recyclerViewMovie.setVisibility(View.VISIBLE);
        recyclerViewMovie.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        recyclerViewMovie.setAdapter(new MovieListAdapter(movieList, new OnItemClickListener<Movie>() {
            @Override
            public void onClick(Movie movie) {
                startActivity(MovieProfileActivity.newIntent(SearchActivity.this, movie));
            }
        }));
    }

    @Override
    public void warnAnyMovieFounded() {
        linearLayoutMovieLoadFailed.setVisibility(View.GONE);
        linearLayoutAnyMovieFounded.setVisibility(View.VISIBLE);
        recyclerViewMovie.setVisibility(View.GONE);
    }

    @Override
    public void warnFailedToLoadMovies() {
        linearLayoutMovieLoadFailed.setVisibility(View.VISIBLE);
        linearLayoutMovieLoadFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.searchMovies(query);
            }
        });
        linearLayoutAnyMovieFounded.setVisibility(View.GONE);
        recyclerViewMovie.setVisibility(View.GONE);
    }

    public void moreMovie(View view) {
        startActivity(SearchMovieActivity.newIntent(this, query));
    }
}
