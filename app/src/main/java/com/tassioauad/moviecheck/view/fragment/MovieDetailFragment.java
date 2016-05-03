package com.tassioauad.moviecheck.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tassioauad.moviecheck.MovieCheckApplication;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.dagger.MovieDetailViewModule;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.presenter.MovieDetailPresenter;
import com.tassioauad.moviecheck.view.MovieDetailView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieDetailFragment extends Fragment implements MovieDetailView {

    @Inject
    MovieDetailPresenter presenter;

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

    private static final String KEY_MOVIE = "MOVIE";

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

        return view;
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
        ratingBarVoteAverage.setRating(voteAverage/2);
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
    }

    @Override
    public void showBackdrop(String backdropUrl) {
        backdropUrl = getString(R.string.imagetmdb_baseurl) + backdropUrl;
        Picasso.with(getActivity()).load(backdropUrl).into(imageViewBackdrop);
    }
}
