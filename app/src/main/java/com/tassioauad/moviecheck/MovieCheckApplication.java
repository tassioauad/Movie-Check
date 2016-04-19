package com.tassioauad.moviecheck;

import android.app.Application;

import com.tassioauad.moviecheck.dagger.ApiModule;
import com.tassioauad.moviecheck.dagger.AppModule;

import dagger.ObjectGraph;

public class MovieCheckApplication extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(
                new Object[]{
                        new AppModule(MovieCheckApplication.this),
                        new ApiModule(),
                }
        );

    }

    public ObjectGraph getObjectGraph() {
        return objectGraph;
    }
}
