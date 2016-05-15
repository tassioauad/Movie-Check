package com.tassioauad.moviecheck;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.tassioauad.moviecheck.dagger.ApiModule;
import com.tassioauad.moviecheck.dagger.AppModule;
import com.tassioauad.moviecheck.dagger.DaoModule;

import dagger.ObjectGraph;

public class MovieCheckApplication extends Application {

    private ObjectGraph objectGraph;
    private Tracker mTracker;

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(
                new Object[]{
                        new AppModule(MovieCheckApplication.this),
                        new ApiModule(),
                        new DaoModule()
                }
        );

    }

    public ObjectGraph getObjectGraph() {
        return objectGraph;
    }
}
