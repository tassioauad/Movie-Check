package com.tassioauad.moviecheck.model.api.impl;

import android.content.Context;
import android.os.AsyncTask;

import com.tassioauad.moviecheck.model.api.GenericApi;
import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.model.api.asynctask.ListPopularMoviesAsyncTask;
import com.tassioauad.moviecheck.model.api.asynctask.ListUpComingMovieAsyncTask;
import com.tassioauad.moviecheck.model.api.resource.MovieResource;

public class MovieApiImpl extends GenericApi implements MovieApi {

    private ListUpComingMovieAsyncTask listUpComingMovieAsyncTask;
    private ListPopularMoviesAsyncTask listPopularMoviesAsyncTask;
    private MovieResource movieResource;

    public MovieApiImpl(Context context, MovieResource movieResource) {
        super(context);
        this.movieResource = movieResource;
    }

    @Override
    public void listUpcomingMovies() {
        verifyServiceResultListener();
        listUpComingMovieAsyncTask = new ListUpComingMovieAsyncTask(getContext(), movieResource, 1);
        listUpComingMovieAsyncTask.setApiResultListener(getApiResultListener());
        listUpComingMovieAsyncTask.execute();
    }

    @Override
    public void listUpcomingMovies(int page) {
        verifyServiceResultListener();
        listUpComingMovieAsyncTask = new ListUpComingMovieAsyncTask(getContext(), movieResource, page);
        listUpComingMovieAsyncTask.setApiResultListener(getApiResultListener());
        listUpComingMovieAsyncTask.execute();
    }

    @Override
    public void listPopularMovies() {
        verifyServiceResultListener();
        listPopularMoviesAsyncTask = new ListPopularMoviesAsyncTask(getContext(), movieResource, 1);
        listPopularMoviesAsyncTask.setApiResultListener(getApiResultListener());
        listPopularMoviesAsyncTask.execute();
    }

    @Override
    public void listPopularMovies(int page) {
        verifyServiceResultListener();
        listPopularMoviesAsyncTask = new ListPopularMoviesAsyncTask(getContext(), movieResource, page);
        listPopularMoviesAsyncTask.setApiResultListener(getApiResultListener());
        listPopularMoviesAsyncTask.execute();
    }

    @Override
    public void cancelAllService() {
        if(listUpComingMovieAsyncTask != null && listUpComingMovieAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            listUpComingMovieAsyncTask.cancel(true);
        }
        if(listPopularMoviesAsyncTask != null && listPopularMoviesAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            listPopularMoviesAsyncTask.cancel(true);
        }
    }
}
