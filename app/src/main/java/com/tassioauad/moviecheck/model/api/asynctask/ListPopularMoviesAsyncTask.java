package com.tassioauad.moviecheck.model.api.asynctask;

import android.content.Context;

import com.tassioauad.moviecheck.model.api.asynctask.exception.BadRequestException;
import com.tassioauad.moviecheck.model.api.resource.MovieResource;
import com.tassioauad.moviecheck.model.entity.Movie;

import java.util.List;
import java.util.Locale;

import retrofit.Response;

import static java.net.HttpURLConnection.HTTP_OK;

public class ListPopularMoviesAsyncTask extends GenericAsyncTask<Void, Void, List<Movie>> {

    private MovieResource movieResource;
    private Integer page;

    public ListPopularMoviesAsyncTask(Context context, MovieResource movieResource, Integer page) {
        super(context);
        this.movieResource = movieResource;
        this.page = page;
    }

    @Override
    protected AsyncTaskResult<List<Movie>> doInBackground(Void... params) {

        try {
            Response<List<Movie>> response = movieResource.listPopular(getApiKey(), page, Locale.getDefault().getLanguage()).execute();
            switch (response.code()) {
                case HTTP_OK:
                    return new AsyncTaskResult<>(response.body());
                default:
                    return new AsyncTaskResult<>(new BadRequestException());
            }
        } catch (Exception ex) {
            return new AsyncTaskResult<>(new BadRequestException());
        }
    }
}
