package com.tassioauad.moviecheck.model.api.impl;

import android.content.Context;
import android.os.AsyncTask;

import com.tassioauad.moviecheck.model.api.CrewApi;
import com.tassioauad.moviecheck.model.api.GenericApi;
import com.tassioauad.moviecheck.model.api.asynctask.ListCrewByMovieAsyncTask;
import com.tassioauad.moviecheck.model.api.resource.CrewResource;
import com.tassioauad.moviecheck.model.entity.Movie;

public class CrewApiImpl extends GenericApi implements CrewApi {

    private CrewResource crewResource;
    private ListCrewByMovieAsyncTask listCrewByMovieAsyncTask;

    public CrewApiImpl(Context context, CrewResource crewResource) {
        super(context);
        this.crewResource = crewResource;
    }

    @Override
    public void listAllByMovie(Movie movie) {
        verifyServiceResultListener();
        listCrewByMovieAsyncTask = new ListCrewByMovieAsyncTask(getContext(), crewResource, movie);
        listCrewByMovieAsyncTask.setApiResultListener(getApiResultListener());
        listCrewByMovieAsyncTask.execute();
    }

    @Override
    public void cancelAllServices() {
        if (listCrewByMovieAsyncTask != null && listCrewByMovieAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            listCrewByMovieAsyncTask.cancel(true);
        }
    }
}
