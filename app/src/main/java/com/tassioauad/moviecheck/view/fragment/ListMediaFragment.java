package com.tassioauad.moviecheck.view.fragment;


import android.content.Intent;
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

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.tassioauad.moviecheck.MovieCheckApplication;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.dagger.ListMediaViewModule;
import com.tassioauad.moviecheck.model.entity.Image;
import com.tassioauad.moviecheck.model.entity.Media;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.Video;
import com.tassioauad.moviecheck.presenter.ListMediaPresenter;
import com.tassioauad.moviecheck.view.ListMediaView;
import com.tassioauad.moviecheck.view.adapter.MediaListAdapter;
import com.tassioauad.moviecheck.view.adapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListMediaFragment extends Fragment implements ListMediaView {

    @Bind(R.id.recyclerview_midia)
    RecyclerView recyclerViewMidia;
    @Bind(R.id.progressbar)
    ProgressBar progressBar;
    @Bind(R.id.linearlayout_anyfounded)
    LinearLayout linearLayoutAnyFounded;
    @Bind(R.id.linearlayout_loadfailed)
    LinearLayout linearLayoutLoadFailed;

    @Inject
    ListMediaPresenter presenter;
    private List<Media> mediaList;
    private Movie movie;
    private static final String BUNDLE_KEY_MEDIALIST = "bundle_key_medialist";
    private static final String KEY_MOVIE = "MOVIE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MovieCheckApplication) getActivity().getApplication()).getObjectGraph()
                .plus(new ListMediaViewModule(this)).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listmedia, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null && savedInstanceState.getParcelableArrayList(BUNDLE_KEY_MEDIALIST) != null) {
            List<Media> mediaList = savedInstanceState.getParcelableArrayList(BUNDLE_KEY_MEDIALIST);
            showMedias(mediaList);
        }

        return view;
    }

    @Override
    public void onResume() {
        if (mediaList == null) {
            movie = getArguments().getParcelable(KEY_MOVIE);
            presenter.loadVideos(movie);
            presenter.loadImages(movie);
        } else {
            showMedias(mediaList);
        }
        super.onResume();
    }

    @Override
    public void onStop() {
        presenter.stop();
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mediaList != null) {
            outState.putParcelableArrayList(BUNDLE_KEY_MEDIALIST, new ArrayList<>(mediaList));
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showLoadingMedias() {
        progressBar.setVisibility(View.VISIBLE);
        linearLayoutAnyFounded.setVisibility(View.GONE);
        linearLayoutLoadFailed.setVisibility(View.GONE);
    }

    @Override
    public void warnAnyMediaFounded() {
        if (mediaList == null) {
            linearLayoutAnyFounded.setVisibility(View.VISIBLE);
            linearLayoutLoadFailed.setVisibility(View.GONE);
            recyclerViewMidia.setVisibility(View.GONE);
        } else {
            Toast.makeText(getActivity(), getActivity().getString(R.string.general_anyfounded), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showVideos(final List<Video> videoList) {
        if (this.mediaList == null) {
            this.mediaList = new ArrayList<Media>(videoList);
        } else {
            this.mediaList.addAll(videoList);
        }
        showMedias(this.mediaList);
    }

    @Override
    public void showImages(List<Image> imageList) {
        if (this.mediaList == null) {
            this.mediaList = new ArrayList<Media>(imageList);
        } else {
            this.mediaList.addAll(imageList);
        }
        showMedias(this.mediaList);
    }

    @Override
    public void showMedias(List<Media> mediaList) {
        this.mediaList = mediaList;
        linearLayoutAnyFounded.setVisibility(View.GONE);
        linearLayoutLoadFailed.setVisibility(View.GONE);
        recyclerViewMidia.setVisibility(View.VISIBLE);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerViewMidia.setLayoutManager(layoutManager);
        recyclerViewMidia.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMidia.setAdapter(new MediaListAdapter(mediaList, new OnItemClickListener<Media>() {
            @Override
            public void onClick(Media media) {
                if (media instanceof Video) {
                    Intent intent = YouTubeStandalonePlayer.createVideoIntent(getActivity(), getString(R.string.youtube_credential), ((Video) media).getKey());
                    startActivity(intent);
                } else if (media instanceof Image) {
                    String photoUrl = getActivity().getString(R.string.imagetmdb_baseurl) + ((Image) media).getFilePath();
                    FullImageDialogFragment.newInstance(photoUrl).show(getActivity().getSupportFragmentManager(), "fullimage");
                }
            }
        }));
    }

    @Override
    public void hideLoadingMedias() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void warnFailedToLoadMedias() {
        if (mediaList == null) {
            linearLayoutAnyFounded.setVisibility(View.GONE);
            linearLayoutLoadFailed.setVisibility(View.VISIBLE);
            linearLayoutLoadFailed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.loadVideos(movie);
                }
            });
            recyclerViewMidia.setVisibility(View.GONE);
        } else {
            Toast.makeText(getActivity(), getActivity().getString(R.string.general_failedtoload), Toast.LENGTH_SHORT).show();
        }
    }

    public static ListMediaFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_MOVIE, movie);
        ListMediaFragment fragment = new ListMediaFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
