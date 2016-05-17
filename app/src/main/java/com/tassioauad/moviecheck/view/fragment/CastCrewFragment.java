package com.tassioauad.moviecheck.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.tassioauad.moviecheck.MovieCheckApplication;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.dagger.CastCrewViewModule;
import com.tassioauad.moviecheck.model.entity.Cast;
import com.tassioauad.moviecheck.model.entity.Crew;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.presenter.CastCrewPresenter;
import com.tassioauad.moviecheck.view.CastCrewView;
import com.tassioauad.moviecheck.view.activity.PersonProfileActivity;
import com.tassioauad.moviecheck.view.adapter.CastListAdapter;
import com.tassioauad.moviecheck.view.adapter.CrewListAdapter;
import com.tassioauad.moviecheck.view.adapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CastCrewFragment extends Fragment implements CastCrewView {

    @Inject
    CastCrewPresenter presenter;

    private List<Cast> castList;
    private List<Crew> crewList;
    private Movie movie;
    private static final String KEY_CREWLIST = "CREWLIST";
    private static final String KEY_CASTLIST = "CASTLIST";
    private static final String KEY_MOVIE = "MOVIE";

    @Bind(R.id.recyclerview_cast)
    RecyclerView recyclerViewCast;
    @Bind(R.id.recyclerview_crew)
    RecyclerView recyclerViewCrew;
    @Bind(R.id.linearlayout_cast_anyfounded)
    LinearLayout linearLayoutAnyCastFounded;
    @Bind(R.id.linearlayout_cast_loadfailed)
    LinearLayout linearLayoutCastLoadFailed;
    @Bind(R.id.progressbar_cast)
    ProgressBar progressBarCast;
    @Bind(R.id.linearlayout_crew_anyfounded)
    LinearLayout linearLayoutAnyCrewFounded;
    @Bind(R.id.linearlayout_crew_loadfailed)
    LinearLayout linearLayoutCrewLoadFailed;
    @Bind(R.id.progressbar_crew)
    ProgressBar progressBarCrew;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MovieCheckApplication) getActivity().getApplication())
                .getObjectGraph().plus(new CastCrewViewModule(this)).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_castcrew, container, false);
        ButterKnife.bind(this, view);

        movie = getArguments().getParcelable(KEY_MOVIE);

        if (savedInstanceState == null) {
            if (castList == null) {
                presenter.loadCast(movie);
            } else if (castList.size() == 0) {
                warnAnyCastFounded();
            } else {
                showCasts(castList);
            }
            if (crewList == null) {
                presenter.loadCrew(movie);
            } else if (crewList.size() == 0) {
                warnAnyCrewFounded();
            } else {
                showCrews(crewList);
            }
        } else {
            crewList = savedInstanceState.getParcelableArrayList(KEY_CREWLIST);
            castList = savedInstanceState.getParcelableArrayList(KEY_CASTLIST);
            if (castList != null) {
                if (castList.size() == 0) {
                    warnAnyCastFounded();
                } else {
                    showCasts(castList);
                }
            } else {
                warnFailedToLoadCasts();
            }
            if (crewList != null) {
                if (crewList.size() == 0) {
                    warnAnyCrewFounded();
                } else {
                    showCrews(crewList);
                }
            } else {
                warnFailedToLoadCrews();
            }
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Tracker defaultTracker = ((MovieCheckApplication) getActivity().getApplication()).getDefaultTracker();
        defaultTracker.setScreenName("List of Cast And Crew Of A Movie Screen");
        defaultTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onStop() {
        presenter.stop();
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (crewList != null) {
            outState.putParcelableArrayList(KEY_CREWLIST, new ArrayList<>(crewList));
        }
        if (castList != null) {
            outState.putParcelableArrayList(KEY_CASTLIST, new ArrayList<>(castList));
        }
    }

    public static CastCrewFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_MOVIE, movie);
        CastCrewFragment fragment = new CastCrewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void hideLoadingCrew() {
        progressBarCrew.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingCrew() {
        progressBarCrew.setVisibility(View.VISIBLE);
        linearLayoutCrewLoadFailed.setVisibility(View.GONE);
        linearLayoutAnyCrewFounded.setVisibility(View.GONE);
    }

    @Override
    public void showCrews(List<Crew> crewList) {
        this.crewList = crewList;
        linearLayoutCrewLoadFailed.setVisibility(View.GONE);
        linearLayoutAnyCrewFounded.setVisibility(View.GONE);
        recyclerViewCrew.setVisibility(View.VISIBLE);
        recyclerViewCrew.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        recyclerViewCrew.setAdapter(new CrewListAdapter(crewList, new OnItemClickListener<Crew>() {
            @Override
            public void onClick(Crew crew, View view) {
                startActivity(PersonProfileActivity.newIntent(getActivity(), crew), ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.imageview_profile), "personPhoto").toBundle());
            }

            @Override
            public void onLongClick(Crew crew, View view) {

            }
        }));
    }

    @Override
    public void warnAnyCrewFounded() {
        crewList = new ArrayList<>();
        linearLayoutCrewLoadFailed.setVisibility(View.GONE);
        linearLayoutAnyCrewFounded.setVisibility(View.VISIBLE);
        recyclerViewCrew.setVisibility(View.GONE);
    }

    @Override
    public void warnFailedToLoadCrews() {
        linearLayoutCrewLoadFailed.setVisibility(View.VISIBLE);
        linearLayoutCrewLoadFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loadCrew(movie);
            }
        });
        linearLayoutAnyCrewFounded.setVisibility(View.GONE);
        recyclerViewCrew.setVisibility(View.GONE);
    }

    @Override
    public void hideLoadingCast() {
        progressBarCast.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingCast() {
        progressBarCast.setVisibility(View.VISIBLE);
        linearLayoutCastLoadFailed.setVisibility(View.GONE);
        linearLayoutAnyCastFounded.setVisibility(View.GONE);
    }

    @Override
    public void showCasts(List<Cast> castList) {
        this.castList = castList;
        linearLayoutCastLoadFailed.setVisibility(View.GONE);
        linearLayoutAnyCastFounded.setVisibility(View.GONE);
        recyclerViewCast.setVisibility(View.VISIBLE);
        recyclerViewCast.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        recyclerViewCast.setAdapter(new CastListAdapter(castList, new OnItemClickListener<Cast>() {
            @Override
            public void onClick(Cast cast, View view) {
                startActivity(PersonProfileActivity.newIntent(getActivity(), cast), ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.imageview_poster), "personPhoto").toBundle());
            }

            @Override
            public void onLongClick(Cast cast, View view) {

            }
        }));
    }

    @Override
    public void warnAnyCastFounded() {
        castList = new ArrayList<>();
        linearLayoutCastLoadFailed.setVisibility(View.GONE);
        linearLayoutAnyCastFounded.setVisibility(View.VISIBLE);
        recyclerViewCast.setVisibility(View.GONE);
    }

    @Override
    public void warnFailedToLoadCasts() {
        linearLayoutCastLoadFailed.setVisibility(View.VISIBLE);
        linearLayoutCastLoadFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loadCast(movie);
            }
        });
        linearLayoutAnyCastFounded.setVisibility(View.GONE);
        recyclerViewCast.setVisibility(View.GONE);
    }
}
