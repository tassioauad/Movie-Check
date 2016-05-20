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
import com.tassioauad.moviecheck.dagger.UserProfileViewModule;
import com.tassioauad.moviecheck.presenter.UserPerfilPresenter;
import com.tassioauad.moviecheck.view.UserProfileView;
import com.tassioauad.moviecheck.view.fragment.ListMovieInterestsFragment;
import com.tassioauad.moviecheck.view.fragment.ListMovieWatchedFragment;
import com.tassioauad.moviecheck.view.fragment.PersonDetailFragment;
import com.tassioauad.moviecheck.view.fragment.UserDetailFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserProfileActivity extends AppCompatActivity implements UserProfileView {

    @Inject
    UserPerfilPresenter presenter;

    @Nullable @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Nullable @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        ((MovieCheckApplication) getApplication()).getObjectGraph().plus(new UserProfileViewModule(this)).inject(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (viewPager != null) {
            viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    switch (position) {
                        case 0:
                            return UserDetailFragment.newInstance();
                        case 1:
                            return ListMovieInterestsFragment.newInstance();
                        case 2:
                            return ListMovieWatchedFragment.newInstance();
                        default:
                            return null;
                    }
                }

                @Override
                public int getCount() {
                    return 3;
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    switch (position) {
                        case 0:
                            return getString(R.string.userprofileactivity_general);
                        case 1:
                            return getString(R.string.userprofileactivity_interest);
                        case 2:
                            return getString(R.string.userprofileactivity_watched);
                        default:
                            return null;
                    }
                }
            });
            tabLayout.setupWithViewPager(viewPager);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_general, UserDetailFragment.newInstance()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_interest, ListMovieInterestsFragment.newInstance()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_classification, ListMovieWatchedFragment.newInstance()).commit();
        }

        presenter.init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Tracker defaultTracker = ((MovieCheckApplication) getApplication()).getDefaultTracker();
        defaultTracker.setScreenName("User Profile Screen");
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

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        return intent;
    }

    @Override
    public void showUserName(String name) {
        getSupportActionBar().setTitle(String.format(getString(R.string.userprofileactivity_title), name));
    }
}
