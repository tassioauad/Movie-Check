package com.tassioauad.moviecheck.view.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tassioauad.moviecheck.MovieCheckApplication;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.dagger.MovieDetailViewModule;
import com.tassioauad.moviecheck.model.entity.Genre;
import com.tassioauad.moviecheck.model.entity.Image;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.presenter.MovieDetailPresenter;
import com.tassioauad.moviecheck.view.MovieDetailView;
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
    private List<Genre> genreList;

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

        if(savedInstanceState == null) {
            if(genreList == null) {
                presenter.loadGenres();
            } else if(genreList.size() > 0) {
                showGenres(genreList);
            }
        } else {
            genreList = savedInstanceState.getParcelableArrayList(KEY_GENRELIST);
            if(genreList != null) {
                showGenres(genreList);
            }
        }

        return view;
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

    public static MovieDetailFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_MOVIE, movie);
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
        ratingBarVoteAverage.setRating(voteAverage / 2);
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
        posterUrl = getString(R.string.imagetmdb_baseurl) + posterUrl;
        Picasso.with(getActivity()).load(posterUrl).into(imageViewPoster);
        final String finalPosterUrl = posterUrl;
        imageViewPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullImageDialogFragment.newInstance(finalPosterUrl).show(getActivity().getSupportFragmentManager(), "fullimage");
            }
        });
    }

    @Override
    public void showBackdrop(String backdropUrl) {
        backdropUrl = getString(R.string.imagetmdb_baseurl) + backdropUrl;
        Picasso.with(getActivity()).load(backdropUrl).into(imageViewBackdrop);
        final String finalBackdropUrl = backdropUrl;
        imageViewBackdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullImageDialogFragment.newInstance(finalBackdropUrl).show(getActivity().getSupportFragmentManager(), "fullimage");
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
            public void onClick(Genre genre) {
                startActivity(ListMoviesByGenreActivity.newIntent(getActivity(), genre));
            }
        }));
    }

    @Override
    public void warnAnyGenreFounded() {
        genreList = new ArrayList<>();
        Toast.makeText(getActivity(), getActivity().getString(R.string.moviedetailfragment_failedtoloadgenre), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoadingGenres() {
        progressBarGenre.setVisibility(View.GONE);
    }
}
