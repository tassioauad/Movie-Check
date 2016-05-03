package com.tassioauad.moviecheck.model.api.asynctask;

import android.content.Context;

import com.tassioauad.moviecheck.model.api.asynctask.exception.BadRequestException;
import com.tassioauad.moviecheck.model.api.resource.MovieResource;
import com.tassioauad.moviecheck.model.entity.Genre;
import com.tassioauad.moviecheck.model.entity.Movie;

import java.util.List;

import retrofit.Response;

import static java.net.HttpURLConnection.HTTP_OK;

public class ListMovieByGenreAsyncTask extends GenericAsyncTask<Void, Void, List<Movie>> {

    private MovieResource movieResource;
    private Genre genre;
    private int page;

    public ListMovieByGenreAsyncTask(Context context, MovieResource movieResource, Genre genre, int page) {
        super(context);
        this.movieResource = movieResource;
        this.genre = genre;
        this.page = page;
    }

    @Override
    protected AsyncTaskResult<List<Movie>> doInBackground(Void... params) {

        try {
            Response<List<Movie>> response = movieResource.listByGenre(genre.getId(), getApiKey(), page).execute();
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
