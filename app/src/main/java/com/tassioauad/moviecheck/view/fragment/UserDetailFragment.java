package com.tassioauad.moviecheck.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;
import com.tassioauad.moviecheck.MovieCheckApplication;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.dagger.UserDetailViewModule;
import com.tassioauad.moviecheck.model.entity.Genre;
import com.tassioauad.moviecheck.model.entity.MovieInterest;
import com.tassioauad.moviecheck.model.entity.User;
import com.tassioauad.moviecheck.presenter.UserDetailPresenter;
import com.tassioauad.moviecheck.view.UserDetailView;
import com.tassioauad.moviecheck.view.activity.FullImageSliderActivity;
import com.tassioauad.moviecheck.view.activity.ListMoviesByGenreActivity;
import com.tassioauad.moviecheck.view.activity.MovieProfileActivity;
import com.tassioauad.moviecheck.view.adapter.GenreListAdapter;
import com.tassioauad.moviecheck.view.adapter.MovieInterestListAdapter;
import com.tassioauad.moviecheck.view.adapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserDetailFragment extends Fragment implements UserDetailView {

    private static final String KEY_GENRELIST = "GENRELIST";
    private static final String KEY_MOVIEINTEREST = "MOVIEINTEREST";
    private List<Genre> genreList;
    private List<MovieInterest> movieInterestList;

    @Inject
    UserDetailPresenter presenter;

    @Bind(R.id.textview_fullname)
    TextView textViewFullname;
    @Bind(R.id.textview_email)
    TextView textViewEmail;
    @Bind(R.id.imageview_photo)
    ImageView imageViewPhoto;
    @Bind(R.id.recyclerview_interests)
    RecyclerView recyclerViewInterests;
    @Bind(R.id.progressbar_genre)
    ProgressBar progressBarGenre;
    @Bind(R.id.recyclerview_genres)
    RecyclerView recyclerViewGenres;
    @Bind(R.id.fab_discovery)
    FloatingActionButton fabDiscovery;
    @Bind(R.id.linearlayout_genre_anyfounded)
    LinearLayout linearLayoutAnyGenreFounded;
    @Bind(R.id.linearlayout_genre_loadfailed)
    LinearLayout linearLayoutFailedToLoadGenre;
    @Bind(R.id.linearlayout_interests_anyfounded)
    LinearLayout linearLayoutAnyInterestsFounded;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MovieCheckApplication) getActivity().getApplication())
                .getObjectGraph().plus(new UserDetailViewModule(this)).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userdetail, container, false);
        ButterKnife.bind(this, view);

        if (movieInterestList == null && savedInstanceState != null) {
            movieInterestList = savedInstanceState.getParcelableArrayList(KEY_MOVIEINTEREST);
        }

        if (genreList == null && savedInstanceState != null) {
            genreList = savedInstanceState.getParcelableArrayList(KEY_GENRELIST);
        }

        if (genreList == null) {
            presenter.loadGenres();
        } else if (genreList.size() > 0) {
            showGenres(genreList);
        } else {
            warnAnyGenreFounded();
        }

        if (movieInterestList == null) {
            presenter.loadUpcomingInterests();
        } else if (movieInterestList.size() > 0) {
            showUpcomingInterests(movieInterestList);
        } else {
            warnAnyInterestFound();
        }

        fabDiscovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        presenter.init();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Tracker defaultTracker = ((MovieCheckApplication) getActivity().getApplication()).getDefaultTracker();
        defaultTracker.setScreenName("User Detail Screen");
        defaultTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (genreList != null) {
            outState.putParcelableArrayList(KEY_GENRELIST, new ArrayList<>(genreList));
        }
        if (movieInterestList != null) {
            outState.putParcelableArrayList(KEY_MOVIEINTEREST, new ArrayList<>(movieInterestList));
        }
        super.onSaveInstanceState(outState);
    }

    public static UserDetailFragment newInstance() {
        UserDetailFragment fragment = new UserDetailFragment();
        return fragment;
    }

    @Override
    public void showFullname(String fulname) {
        textViewFullname.setText(fulname);
    }

    @Override
    public void showEmail(String email) {
        textViewEmail.setText(email);
    }

    @Override
    public void showPhoto(final String photoUrl) {
        Picasso.with(getActivity()).load(photoUrl).into(imageViewPhoto);
        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(FullImageSliderActivity.newIntent(getActivity(), photoUrl));
            }
        });
    }

    @Override
    public void showUser(User user) {
        showPhoto(user.getPhotoUrl());
        showFullname(user.getName());
        showEmail(user.getEmail());
    }

    @Override
    public void showUpcomingInterests(List<MovieInterest> movieInterests) {
        this.movieInterestList = movieInterests;
        linearLayoutAnyInterestsFounded.setVisibility(View.GONE);
        recyclerViewInterests.setVisibility(View.VISIBLE);
        recyclerViewInterests.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerViewInterests.setAdapter(new MovieInterestListAdapter(movieInterests, new OnItemClickListener<MovieInterest>() {
            @Override
            public void onClick(MovieInterest movieInterest, View view) {
                startActivity(MovieProfileActivity.newIntent(getActivity(), movieInterest.getMovie()), ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()).toBundle());
            }

            @Override
            public void onLongClick(MovieInterest movieInterest, View view) {

            }
        }));
    }

    @Override
    public void showLoadingGenres() {
        progressBarGenre.setVisibility(View.VISIBLE);
    }

    @Override
    public void warnFailedOnLoadGenres() {
        linearLayoutAnyGenreFounded.setVisibility(View.GONE);
        linearLayoutFailedToLoadGenre.setVisibility(View.VISIBLE);
        recyclerViewGenres.setVisibility(View.GONE);
    }

    @Override
    public void warnAnyInterestFound() {
        linearLayoutAnyInterestsFounded.setVisibility(View.VISIBLE);
        recyclerViewInterests.setVisibility(View.GONE);
    }

    @Override
    public void showGenres(List<Genre> genreList) {
        this.genreList = genreList;
        linearLayoutAnyGenreFounded.setVisibility(View.GONE);
        linearLayoutFailedToLoadGenre.setVisibility(View.GONE);
        recyclerViewGenres.setVisibility(View.VISIBLE);
        recyclerViewGenres.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        recyclerViewGenres.setAdapter(new GenreListAdapter(genreList, new OnItemClickListener<Genre>() {
            @Override
            public void onClick(Genre genre, View view) {
                startActivity(ListMoviesByGenreActivity.newIntent(getActivity(), genre), ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()).toBundle());
            }

            @Override
            public void onLongClick(Genre genre, View view) {

            }
        }));
    }

    @Override
    public void warnAnyGenreFounded() {
        genreList = new ArrayList<>();
        linearLayoutAnyGenreFounded.setVisibility(View.VISIBLE);
        linearLayoutFailedToLoadGenre.setVisibility(View.GONE);
        recyclerViewGenres.setVisibility(View.GONE);
    }

    @Override
    public void hideLoadingGenres() {
        progressBarGenre.setVisibility(View.GONE);
    }


}
