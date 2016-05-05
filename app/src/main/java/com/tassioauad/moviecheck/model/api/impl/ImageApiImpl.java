package com.tassioauad.moviecheck.model.api.impl;

import android.content.Context;
import android.os.AsyncTask;

import com.tassioauad.moviecheck.model.api.GenericApi;
import com.tassioauad.moviecheck.model.api.ImageApi;
import com.tassioauad.moviecheck.model.api.asynctask.ListImageByMovieAsyncTask;
import com.tassioauad.moviecheck.model.api.resource.ImageResource;
import com.tassioauad.moviecheck.model.entity.Movie;

public class ImageApiImpl extends GenericApi implements ImageApi {

    private ImageResource imageResource;
    private ListImageByMovieAsyncTask listImageByMovieAsyncTask;

    public ImageApiImpl(Context context, ImageResource imageResource) {
        super(context);
        this.imageResource = imageResource;
    }

    @Override
    public void listAllByMovie(Movie movie) {
        verifyServiceResultListener();
        listImageByMovieAsyncTask = new ListImageByMovieAsyncTask(getContext(), imageResource, movie);
        listImageByMovieAsyncTask.setApiResultListener(getApiResultListener());
        listImageByMovieAsyncTask.execute();
    }

    @Override
    public void cancelAllServices() {
        if(listImageByMovieAsyncTask != null && listImageByMovieAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            listImageByMovieAsyncTask.cancel(true);
        }
    }
}
