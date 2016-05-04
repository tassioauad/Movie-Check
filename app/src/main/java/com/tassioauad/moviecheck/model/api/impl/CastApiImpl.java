package com.tassioauad.moviecheck.model.api.impl;

import android.content.Context;
import android.os.AsyncTask;

import com.tassioauad.moviecheck.model.api.CastApi;
import com.tassioauad.moviecheck.model.api.GenericApi;
import com.tassioauad.moviecheck.model.api.asynctask.ListCastByMovieAsyncTask;
import com.tassioauad.moviecheck.model.api.resource.CastResource;
import com.tassioauad.moviecheck.model.entity.Movie;

public class CastApiImpl extends GenericApi implements CastApi {

    private CastResource castResource;
    private ListCastByMovieAsyncTask listCastByMovieAsyncTask;

    public CastApiImpl(Context context, CastResource castResource) {
        super(context);
        this.castResource = castResource;
    }

    @Override
    public void listAllByMovie(Movie movie) {
        verifyServiceResultListener();
        listCastByMovieAsyncTask = new ListCastByMovieAsyncTask(getContext(), castResource, movie);
        listCastByMovieAsyncTask.setApiResultListener(getApiResultListener());
        listCastByMovieAsyncTask.execute();
    }

    @Override
    public void cancelAllServices() {
        if (listCastByMovieAsyncTask != null && listCastByMovieAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            listCastByMovieAsyncTask.cancel(true);
        }
    }
}
