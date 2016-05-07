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
import com.tassioauad.moviecheck.dagger.SearchMovieViewModule;
import com.tassioauad.moviecheck.dagger.SearchPersonViewModule;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.Person;
import com.tassioauad.moviecheck.presenter.SearchPersonPresenter;
import com.tassioauad.moviecheck.view.SearchPersonView;
import com.tassioauad.moviecheck.view.adapter.ListViewAdapterWithPagination;
import com.tassioauad.moviecheck.view.adapter.MovieListAdapter;
import com.tassioauad.moviecheck.view.adapter.OnItemClickListener;
import com.tassioauad.moviecheck.view.adapter.OnShowMoreListener;
import com.tassioauad.moviecheck.view.adapter.PersonListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchPersonActivity extends AppCompatActivity implements SearchPersonView {

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
    SearchPersonPresenter presenter;
    private List<Person> personList;
    private Integer page = 1;
    private String query;
    private Integer columns = 3;
    private int scrollToItem;
    private ListViewAdapterWithPagination listViewAdapter;
    private static final String BUNDLE_KEY_PERSONLIST = "bundle_key_personlist";
    private static final String BUNDLE_KEY_PAGE = "bundle_key_page";
    private static final String KEY_QUERY = "QUERY";
    private final int itensPerPage = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchmovie);
        ButterKnife.bind(this);
        ((MovieCheckApplication) getApplication()).getObjectGraph().plus(new SearchPersonViewModule(this)).inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        query = getIntent().getStringExtra(KEY_QUERY);
        getSupportActionBar().setTitle(query);
        getSupportActionBar().setSubtitle(getString(R.string.searchpersonactivity_celebrities));

        if (savedInstanceState == null) {
            presenter.search(query, page);
        } else {
            List<Person> personList = savedInstanceState.getParcelableArrayList(BUNDLE_KEY_PERSONLIST);
            page = savedInstanceState.getInt(BUNDLE_KEY_PAGE);
            showPerson(personList);
        }
    }

    @Override
    protected void onStop() {
        presenter.stop();
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (personList != null) {
            outState.putParcelableArrayList(BUNDLE_KEY_PERSONLIST, new ArrayList<>(personList));
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
    public void showLoadingPerson() {
        progressBar.setVisibility(View.VISIBLE);
        linearLayoutAnyFounded.setVisibility(View.GONE);
        linearLayoutLoadFailed.setVisibility(View.GONE);
    }

    @Override
    public void warnAnyPersonFounded() {
        if(personList == null) {
            linearLayoutAnyFounded.setVisibility(View.VISIBLE);
            linearLayoutLoadFailed.setVisibility(View.GONE);
            recyclerViewMovies.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, getString(R.string.general_anyfounded), Toast.LENGTH_SHORT).show();
            listViewAdapter.withShowMoreButton(false);
            recyclerViewMovies.setAdapter(listViewAdapter);
        }
    }

    @Override
    public void showPerson(final List<Person> personList) {
        if (this.personList == null) {
            this.personList = personList;
        } else {
            this.personList.addAll(personList);
        }
        linearLayoutAnyFounded.setVisibility(View.GONE);
        linearLayoutLoadFailed.setVisibility(View.GONE);
        recyclerViewMovies.setVisibility(View.VISIBLE);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, columns, GridLayoutManager.VERTICAL, false);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position >= SearchPersonActivity.this.personList.size() ? columns : 1;
            }
        });
        recyclerViewMovies.setLayoutManager(layoutManager);
        recyclerViewMovies.setItemAnimator(new DefaultItemAnimator());
        listViewAdapter = new ListViewAdapterWithPagination(
                new PersonListAdapter(this.personList, new OnItemClickListener<Person>() {
                    @Override
                    public void onClick(Person person) {
                        startActivity(PersonProfileActivity.newIntent(SearchPersonActivity.this, person));
                    }
                }
                ),
                new OnShowMoreListener() {
                    @Override
                    public void showMore() {
                        scrollToItem = layoutManager.findFirstVisibleItemPosition();
                        presenter.search(query, ++page);
                    }
                },
                itensPerPage);
        recyclerViewMovies.setAdapter(listViewAdapter);
        recyclerViewMovies.scrollToPosition(scrollToItem);
    }

    @Override
    public void hideLoadingPerson() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void warnFailedToLoadPerson() {
        if(personList == null) {
            linearLayoutAnyFounded.setVisibility(View.GONE);
            linearLayoutLoadFailed.setVisibility(View.VISIBLE);
            linearLayoutLoadFailed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.search(query, page);
                }
            });
            recyclerViewMovies.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, getString(R.string.general_failedtoload), Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent newIntent(Context context, String query) {
        Intent intent = new Intent(context, SearchPersonActivity.class);
        intent.putExtra(KEY_QUERY, query);
        return intent;
    }
}
