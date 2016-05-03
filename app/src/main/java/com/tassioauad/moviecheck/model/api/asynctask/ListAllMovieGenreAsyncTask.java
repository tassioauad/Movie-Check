package com.tassioauad.moviecheck.model.api.asynctask;

import android.content.Context;

import com.tassioauad.moviecheck.model.api.asynctask.exception.BadRequestException;
import com.tassioauad.moviecheck.model.api.resource.GenreResource;
import com.tassioauad.moviecheck.model.entity.Genre;

import java.util.List;

import retrofit.Response;

import static java.net.HttpURLConnection.HTTP_OK;

public class ListAllMovieGenreAsyncTask extends GenericAsyncTask<Void, Void, List<Genre>> {

    private GenreResource genreResource;

    public ListAllMovieGenreAsyncTask(Context context, GenreResource genreResource) {
        super(context);
        this.genreResource = genreResource;
    }

    @Override
    protected AsyncTaskResult<List<Genre>> doInBackground(Void... params) {

        try {
            Response<List<Genre>> response = genreResource.listAllofMovie(getApiKey()).execute();
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
