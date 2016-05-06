package com.tassioauad.moviecheck.model.api.impl;

import android.content.Context;
import android.os.AsyncTask;

import com.tassioauad.moviecheck.model.api.CrewApi;
import com.tassioauad.moviecheck.model.api.GenericApi;
import com.tassioauad.moviecheck.model.api.asynctask.ListCrewByMovieAsyncTask;
import com.tassioauad.moviecheck.model.api.asynctask.ListMovieByCrewAsyncTask;
import com.tassioauad.moviecheck.model.api.resource.CrewResource;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.Person;

public class CrewApiImpl extends GenericApi implements CrewApi {

    private CrewResource crewResource;
    private ListCrewByMovieAsyncTask listCrewByMovieAsyncTask;
    private ListMovieByCrewAsyncTask listMovieByCrewAsyncTask;

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
    public void listMoviesByCrew(Person person) {
        verifyServiceResultListener();
        listMovieByCrewAsyncTask = new ListMovieByCrewAsyncTask(getContext(), crewResource, person);
        listMovieByCrewAsyncTask.setApiResultListener(getApiResultListener());
        listMovieByCrewAsyncTask.execute();
    }

    @Override
    public void cancelAllServices() {
        if (listCrewByMovieAsyncTask != null && listCrewByMovieAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            listCrewByMovieAsyncTask.cancel(true);
        }
        if (listMovieByCrewAsyncTask != null && listMovieByCrewAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            listMovieByCrewAsyncTask.cancel(true);
        }
    }
}
