package com.tassioauad.moviecheck.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.tassioauad.moviecheck.MovieCheckApplication;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.dagger.MovieProfileViewModule;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.presenter.MovieProfilePresenter;
import com.tassioauad.moviecheck.view.MovieProfileView;
import com.tassioauad.moviecheck.view.fragment.CastCrewFragment;
import com.tassioauad.moviecheck.view.fragment.ListMovieMediaFragment;
import com.tassioauad.moviecheck.view.fragment.ListReviewFragment;
import com.tassioauad.moviecheck.view.fragment.MovieDetailFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieProfileActivity extends AppCompatActivity implements MovieProfileView {

    private static final String KEY_MOVIE = "MOVIE";

    @Inject
    MovieProfilePresenter presenter;

    @Nullable
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movieprofile);
        ButterKnife.bind(this);
        ((MovieCheckApplication) getApplication()).getObjectGraph().plus(new MovieProfileViewModule(this)).inject(this);

        setSupportActionBar(toolbar);

        final Movie movie = getIntent().getParcelableExtra(KEY_MOVIE);

        presenter.init(movie);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (viewPager != null) {
            viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    switch (position) {
                        case 0:
                            return MovieDetailFragment.newInstance(movie);
                        case 1:
                            return CastCrewFragment.newInstance(movie);
                        case 2:
                            return ListReviewFragment.newInstance(movie);
                        case 3:
                            return ListMovieMediaFragment.newInstance(movie);
                        default:
                            return null;
                    }
                }

                @Override
                public int getCount() {
                    return 4;
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    switch (position) {
                        case 0:
                            return getString(R.string.movieprofileactivity_general);
                        case 1:
                            return getString(R.string.movieprofileactivity_castcrew);
                        case 2:
                            return getString(R.string.movieprofileactivity_reviews);
                        case 3:
                            return getString(R.string.movieprofileactivity_media);
                        default:
                            return null;
                    }
                }
            });
            tabLayout.setupWithViewPager(viewPager);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_media, ListMovieMediaFragment.newInstance(movie)).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_detail, MovieDetailFragment.newInstance(movie)).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_castcrew, CastCrewFragment.newInstance(movie)).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_review, ListReviewFragment.newInstance(movie)).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tracker defaultTracker = ((MovieCheckApplication) getApplication()).getDefaultTracker();
        defaultTracker.setScreenName("Movie Profile Screen");
        defaultTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieProfileActivity.class);
        intent.putExtra(KEY_MOVIE, movie);
        return intent;
    }

    @Override
    public void showMovieName(String title) {
        getSupportActionBar().setTitle(title);
    }
}
