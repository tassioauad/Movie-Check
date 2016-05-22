package com.tassioauad.moviecheck.view.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.tassioauad.moviecheck.MovieCheckApplication;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.dagger.ListMovieInterestViewModule;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.MovieInterest;
import com.tassioauad.moviecheck.presenter.ListMovieInterestsPresenter;
import com.tassioauad.moviecheck.view.ListMovieInterestsView;
import com.tassioauad.moviecheck.view.activity.MovieProfileActivity;
import com.tassioauad.moviecheck.view.adapter.MovieInterestListAdapter;
import com.tassioauad.moviecheck.view.adapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListMovieInterestsFragment extends Fragment implements ListMovieInterestsView {

    private static final String KEY_MOVIEINTEREST = "MOVIEINTEREST";
    @Bind(R.id.recyclerview_movieinterests)
    RecyclerView recyclerViewMovieInterests;
    @Bind(R.id.linearlayout_anyfounded)
    LinearLayout linearLayoutAnyFounded;
    MovieInterestListAdapter movieInterestListAdapter;

    @Inject
    ListMovieInterestsPresenter presenter;
    private List<MovieInterest> movieInterestList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MovieCheckApplication) getActivity().getApplication()).getObjectGraph()
                .plus(new ListMovieInterestViewModule(this, getActivity())).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listmovieinterest, container, false);
        ButterKnife.bind(this, view);

        if(movieInterestList == null && savedInstanceState != null) {
            movieInterestList = savedInstanceState.getParcelableArrayList(KEY_MOVIEINTEREST);
        }

        if(movieInterestList == null) {
            presenter.loadMovieInterests();
        } else if(movieInterestList.size() > 0) {
            showMovieInterests(movieInterestList);
        } else {
            warnAnyInterestFounded();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Tracker defaultTracker = ((MovieCheckApplication) getActivity().getApplication()).getDefaultTracker();
        defaultTracker.setScreenName("List Movie Interests of the User Screen");
        defaultTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public static ListMovieInterestsFragment newInstance() {
        ListMovieInterestsFragment fragment = new ListMovieInterestsFragment();
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(movieInterestList != null) {
            outState.putParcelableArrayList(KEY_MOVIEINTEREST, new ArrayList<>(movieInterestList));
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showMovieInterests(List<MovieInterest> movieInterestList) {
        this.movieInterestList = movieInterestList;
        linearLayoutAnyFounded.setVisibility(View.GONE);
        recyclerViewMovieInterests.setVisibility(View.VISIBLE);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerViewMovieInterests.setLayoutManager(layoutManager);
        recyclerViewMovieInterests.setItemAnimator(new DefaultItemAnimator());
        movieInterestListAdapter = new MovieInterestListAdapter(movieInterestList, new OnItemClickListener<MovieInterest>() {
            @Override
            public void onClick(MovieInterest movieInterest, View view) {
                startActivity(MovieProfileActivity.newIntent(getActivity(), movieInterest.getMovie()));
            }

            @Override
            public void onLongClick(final MovieInterest movieInterest, View view) {
                new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.DialogLight)).setItems(new CharSequence[]{getActivity().getString(R.string.listmovieinterestfragment_remove)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                removeMovieInterest(movieInterest);
                                break;
                        }
                    }
                }).create().show();
            }
        });
        recyclerViewMovieInterests.setAdapter(movieInterestListAdapter);
    }

    private void removeMovieInterest(MovieInterest movieInterest) {
        presenter.remove(movieInterest);
        movieInterestListAdapter.remove(movieInterestList.indexOf(movieInterest));
        movieInterestList.remove(movieInterest);
    }

    @Override
    public void warnAnyInterestFounded() {
        recyclerViewMovieInterests.setVisibility(View.GONE);
        linearLayoutAnyFounded.setVisibility(View.VISIBLE);
    }

    @Override
    public void warnMovieRemoved(Movie movie) {
        Toast.makeText(getActivity(), String.format(getString(R.string.listmovieinterestfragment_movieremoved), movie.getTitle()), Toast.LENGTH_SHORT).show();
    }
}
