package com.tassioauad.moviecheck.model.api.asynctask;

import android.content.Context;

import com.tassioauad.moviecheck.model.api.asynctask.exception.BadRequestException;
import com.tassioauad.moviecheck.model.api.resource.ImageResource;
import com.tassioauad.moviecheck.model.api.resource.VideoResource;
import com.tassioauad.moviecheck.model.entity.Image;
import com.tassioauad.moviecheck.model.entity.Movie;

import java.util.List;

import retrofit.Response;

import static java.net.HttpURLConnection.HTTP_OK;

public class ListImageByMovieAsyncTask extends GenericAsyncTask<Void, Void, List<Image>> {

    private ImageResource imageResource;
    private Movie movie;

    public ListImageByMovieAsyncTask(Context context, ImageResource imageResource, Movie movie) {
        super(context);
        this.imageResource = imageResource;
        this.movie = movie;
    }

    @Override
    protected AsyncTaskResult<List<Image>> doInBackground(Void... params) {

        try {
            Response<List<Image>> response = imageResource.listByMovie(movie.getId(), getApiKey()).execute();
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
