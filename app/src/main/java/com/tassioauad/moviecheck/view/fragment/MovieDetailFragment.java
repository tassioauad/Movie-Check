package com.tassioauad.moviecheck.view.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;
import com.tassioauad.moviecheck.MovieCheckApplication;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.dagger.MovieDetailViewModule;
import com.tassioauad.moviecheck.model.entity.Genre;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.presenter.MovieDetailPresenter;
import com.tassioauad.moviecheck.view.MovieDetailView;
import com.tassioauad.moviecheck.view.activity.FullImageSliderActivity;
import com.tassioauad.moviecheck.view.activity.ListMoviesByGenreActivity;
import com.tassioauad.moviecheck.view.adapter.GenreListAdapter;
import com.tassioauad.moviecheck.view.adapter.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieDetailFragment extends Fragment implements MovieDetailView {

    @Inject
    MovieDetailPresenter presenter;
    private static final String KEY_MOVIE = "MOVIE";
    private static final String KEY_GENRELIST = "GENRELIST";
    private static final String KEY_ALLOWINTEREST = "ALLOWINTEREST";
    private List<Genre> genreList;
    RatingBar.OnRatingBarChangeListener onRatingBarChangeListener;

    @Bind(R.id.textview_votecount)
    TextView textViewVoteCount;
    @Bind(R.id.textview_releasedate)
    TextView textViewReleaseDate;
    @Bind(R.id.ratingbar_vote)
    RatingBar ratingBarVoteAverage;
    @Bind(R.id.imageview_backdrop)
    ImageView imageViewBackdrop;
    @Bind(R.id.imageview_poster)
    ImageView imageViewPoster;
    @Bind(R.id.textview_overview)
    TextView textViewOverview;
    @Bind(R.id.recyclerview_genres)
    RecyclerView recyclerViewGenres;
    @Bind(R.id.progressbar_genre)
    ProgressBar progressBarGenre;
    @Bind(R.id.fab_interest)
    FloatingActionButton fabInterest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MovieCheckApplication) getActivity().getApplication())
                .getObjectGraph().plus(new MovieDetailViewModule(this)).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moviedetail, container, false);
        ButterKnife.bind(this, view);

        presenter.init((Movie) getArguments().getParcelable(KEY_MOVIE));

        if (genreList == null && savedInstanceState != null) {
            genreList = savedInstanceState.getParcelableArrayList(KEY_GENRELIST);
        }

        if (genreList == null) {
            presenter.loadGenres();
        } else if (genreList.size() > 0) {
            showGenres(genreList);
        }

        fabInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.checkInterest();
            }
        });

        onRatingBarChangeListener = new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating == 0f) {
                    presenter.removeClassification();
                } else {
                    presenter.informUserClassification(rating);
                }
            }
        };

        ratingBarVoteAverage.setOnRatingBarChangeListener(onRatingBarChangeListener);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Tracker defaultTracker = ((MovieCheckApplication) getActivity().getApplication()).getDefaultTracker();
        defaultTracker.setScreenName("Movie Detail Screen");
        defaultTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onStop() {
        presenter.stop();
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (genreList != null) {
            outState.putParcelableArrayList(KEY_GENRELIST, new ArrayList<>(genreList));
        }
        super.onSaveInstanceState(outState);
    }

    public static MovieDetailFragment newInstance(Movie movie, boolean allowInterest) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_MOVIE, movie);
        args.putBoolean(KEY_ALLOWINTEREST, allowInterest);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showVoteCount(long voteCount) {
        textViewVoteCount.setText(String.format(getString(R.string.moviedetailfragment_votecount), voteCount));
    }

    @Override
    public void showVoteAverage(float voteAverage) {
        textViewVoteCount.setVisibility(View.VISIBLE);
        RatingBar newRatingBar = new RatingBar(new ContextThemeWrapper(getActivity(), R.style.RatingBarAccent));
        newRatingBar.setRating(voteAverage / 2);
        newRatingBar.setIsIndicator(true);
        newRatingBar.setOnRatingBarChangeListener(onRatingBarChangeListener);
        ((ViewGroup) ratingBarVoteAverage.getParent()).addView(newRatingBar, 0);
        ((ViewGroup) ratingBarVoteAverage.getParent()).removeView(ratingBarVoteAverage);
        ratingBarVoteAverage = newRatingBar;
    }

    @Override
    public void showOverview(String overview) {
        textViewOverview.setText(overview);
    }

    @Override
    public void showReleaseDate(Date releaseDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getString(R.string.general_date), Locale.getDefault());
        textViewReleaseDate.setText(simpleDateFormat.format(releaseDate));
    }

    @Override
    public void showPoster(String posterUrl) {
        final String pathUrl = getString(R.string.imagetmdb_baseurl) + posterUrl;
        Picasso.with(getActivity()).load(pathUrl).into(imageViewPoster);
        imageViewPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(FullImageSliderActivity.newIntent(getActivity(), pathUrl));
            }
        });
    }

    @Override
    public void showBackdrop(String backdropUrl) {
        final String pathUrl = getString(R.string.imagetmdb_baseurl) + backdropUrl;
        Picasso.with(getActivity()).load(pathUrl).into(imageViewBackdrop);
        imageViewBackdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(FullImageSliderActivity.newIntent(getActivity(), pathUrl));
            }
        });
    }

    @Override
    public void showLoadingGenres() {
        progressBarGenre.setVisibility(View.VISIBLE);
    }

    @Override
    public void warnFailedOnLoadGenres() {
        Toast.makeText(getActivity(), getActivity().getString(R.string.moviedetailfragment_failedtoloadgenre), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGenres(List<Genre> genreList) {
        this.genreList = genreList;
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
    }

    @Override
    public void hideLoadingGenres() {
        progressBarGenre.setVisibility(View.GONE);
    }

    @Override
    public void disableToCheckInterest() {
        fabInterest.setVisibility(View.GONE);
    }

    @Override
    public void enableToCheckInterest() {
        if (getArguments().getBoolean(KEY_ALLOWINTEREST)) {
            fabInterest.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void checkInterest() {
        fabInterest.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.accent)));
    }

    @Override
    public void uncheckInterest() {
        fabInterest.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
    }

    @Override
    public void showUserClassification(Float classification) {
        textViewVoteCount.setVisibility(View.INVISIBLE);
        RatingBar newRatingBar = new RatingBar(new ContextThemeWrapper(getActivity(), R.style.RatingBarRed));
        newRatingBar.setRating(classification);
        newRatingBar.setIsIndicator(false);
        newRatingBar.setOnRatingBarChangeListener(onRatingBarChangeListener);
        ((ViewGroup) ratingBarVoteAverage.getParent()).addView(newRatingBar, 0);
        ((ViewGroup) ratingBarVoteAverage.getParent()).removeView(ratingBarVoteAverage);
        ratingBarVoteAverage = newRatingBar;

    }

    @Override
    public void enableToClassify() {
        ratingBarVoteAverage.setIsIndicator(false);
    }

    @Override
    public void warnRemovedFromWatched() {
        Toast.makeText(getActivity(), R.string.moviedetailsfragment_removedfromwatched, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void warnAddedAsWatched() {
        Toast.makeText(getActivity(), R.string.moviedetailsfragment_addedaswatched, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void warmAddedAsInteresting() {
        Toast.makeText(getActivity(), R.string.moviedetailsfragment_addedtowatchlater, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void removedFromInteresting() {
        Toast.makeText(getActivity(), R.string.moviedetailfragment_movieremovedwatchlater, Toast.LENGTH_SHORT).show();
    }
}
