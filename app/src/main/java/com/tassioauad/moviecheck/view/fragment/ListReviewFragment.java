package com.tassioauad.moviecheck.view.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tassioauad.moviecheck.MovieCheckApplication;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.dagger.ListReviewViewModule;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.Review;
import com.tassioauad.moviecheck.presenter.ListReviewPresenter;
import com.tassioauad.moviecheck.view.ListReviewView;
import com.tassioauad.moviecheck.view.adapter.ListViewAdapterWithPagination;
import com.tassioauad.moviecheck.view.adapter.OnItemClickListener;
import com.tassioauad.moviecheck.view.adapter.OnShowMoreListener;
import com.tassioauad.moviecheck.view.adapter.ReviewListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListReviewFragment extends Fragment implements ListReviewView {

    @Bind(R.id.recyclerview_reviews)
    RecyclerView recyclerViewReviews;
    @Bind(R.id.progressbar)
    ProgressBar progressBar;
    @Bind(R.id.linearlayout_anyfounded)
    LinearLayout linearLayoutAnyFounded;
    @Bind(R.id.linearlayout_loadfailed)
    LinearLayout linearLayoutLoadFailed;

    @Inject
    ListReviewPresenter presenter;
    private List<Review> reviewList;
    private int scrollToItem;
    private int page = 1;
    private Movie movie;
    private ListViewAdapterWithPagination listViewAdapter;
    private static final String BUNDLE_KEY_REVIEWLIST = "bundle_key_reviewlist";
    private static final String BUNDLE_KEY_PAGE = "bundle_key_page";
    private static final String KEY_MOVIE = "MOVIE";
    private final int itensPerPage = 20;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MovieCheckApplication) getActivity().getApplication()).getObjectGraph()
                .plus(new ListReviewViewModule(this)).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listreview, container, false);
        ButterKnife.bind(this, view);


        if (savedInstanceState == null) {
            if (reviewList == null) {
                movie = getArguments().getParcelable(KEY_MOVIE);
                presenter.loadReviews(movie, page);
            } else if (reviewList.size() == 0) {
                warnAnyReviewFounded();
            } else {
                showReviews(reviewList);
            }
        } else {
            List<Review> reviewList = savedInstanceState.getParcelableArrayList(BUNDLE_KEY_REVIEWLIST);
            if (reviewList == null) {
                movie = getArguments().getParcelable(KEY_MOVIE);
                presenter.loadReviews(movie, page);
            } else if (reviewList.size() == 0) {
                warnAnyReviewFounded();
            } else {
                page = savedInstanceState.getInt(BUNDLE_KEY_PAGE);
                showReviews(reviewList);
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
        if (reviewList != null) {
            outState.putParcelableArrayList(BUNDLE_KEY_REVIEWLIST, new ArrayList<>(reviewList));
        }
        outState.putInt(BUNDLE_KEY_PAGE, page);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showLoadingReview() {
        progressBar.setVisibility(View.VISIBLE);
        linearLayoutAnyFounded.setVisibility(View.GONE);
        linearLayoutLoadFailed.setVisibility(View.GONE);
    }

    @Override
    public void warnAnyReviewFounded() {
        if (reviewList == null || reviewList.size() == 0) {
            reviewList = new ArrayList<>();
            linearLayoutAnyFounded.setVisibility(View.VISIBLE);
            linearLayoutLoadFailed.setVisibility(View.GONE);
            recyclerViewReviews.setVisibility(View.GONE);
        } else {
            Toast.makeText(getActivity(), getActivity().getString(R.string.general_anyfounded), Toast.LENGTH_SHORT).show();
            listViewAdapter.withShowMoreButton(false);
            recyclerViewReviews.setAdapter(listViewAdapter);
        }
    }

    @Override
    public void showReviews(final List<Review> reviewList) {
        if (this.reviewList == null) {
            this.reviewList = reviewList;
        } else {
            this.reviewList.addAll(reviewList);
        }
        linearLayoutAnyFounded.setVisibility(View.GONE);
        linearLayoutLoadFailed.setVisibility(View.GONE);
        recyclerViewReviews.setVisibility(View.VISIBLE);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerViewReviews.setLayoutManager(layoutManager);
        recyclerViewReviews.setItemAnimator(new DefaultItemAnimator());
        listViewAdapter = new ListViewAdapterWithPagination(
                new ReviewListAdapter(this.reviewList, new OnItemClickListener<Review>() {
                    @Override
                    public void onClick(Review review) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(review.getUrl()));
                        startActivity(browserIntent);
                    }
                }),
                new OnShowMoreListener() {
                    @Override
                    public void showMore() {
                        scrollToItem = layoutManager.findFirstVisibleItemPosition();
                        presenter.loadReviews(movie, ++page);
                    }
                },
                itensPerPage);
        recyclerViewReviews.setAdapter(listViewAdapter);
        recyclerViewReviews.scrollToPosition(scrollToItem);
    }

    @Override
    public void hideLoadingReviews() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void warnFailedToLoadReviews() {
        if (reviewList == null) {
            linearLayoutAnyFounded.setVisibility(View.GONE);
            linearLayoutLoadFailed.setVisibility(View.VISIBLE);
            linearLayoutLoadFailed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.loadReviews(movie, page);
                }
            });
            recyclerViewReviews.setVisibility(View.GONE);
        } else {
            Toast.makeText(getActivity(), getActivity().getString(R.string.general_failedtoload), Toast.LENGTH_SHORT).show();
        }
    }

    public static ListReviewFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_MOVIE, movie);
        ListReviewFragment fragment = new ListReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
