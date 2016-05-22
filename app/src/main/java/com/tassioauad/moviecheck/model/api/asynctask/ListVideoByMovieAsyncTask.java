package com.tassioauad.moviecheck.model.api.asynctask;

import android.content.Context;

import com.tassioauad.moviecheck.model.api.asynctask.exception.BadRequestException;
import com.tassioauad.moviecheck.model.api.resource.CastResource;
import com.tassioauad.moviecheck.model.api.resource.VideoResource;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.Video;

import java.util.List;

import retrofit.Response;

import static java.net.HttpURLConnection.HTTP_OK;

public class ListVideoByMovieAsyncTask extends GenericAsyncTask<Void, Void, List<Video>> {

    private VideoResource videoResource;
    private Movie movie;

    public ListVideoByMovieAsyncTask(Context context, VideoResource videoResource, Movie movie) {
        super(context);
        this.videoResource = videoResource;
        this.movie = movie;
    }

    @Override
    protected AsyncTaskResult<List<Video>> doInBackground(Void... params) {

        try {
            Response<List<Video>> response = videoResource.listAllByMovie(movie.getId(), getApiKey()).execute();
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
