package com.tassioauad.moviecheck.model.api.asynctask;

import android.content.Context;

import com.tassioauad.moviecheck.model.api.asynctask.exception.BadRequestException;
import com.tassioauad.moviecheck.model.api.resource.CrewResource;
import com.tassioauad.moviecheck.model.api.resource.MovieResource;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.Person;

import java.util.List;
import java.util.Locale;

import retrofit.Response;

import static java.net.HttpURLConnection.HTTP_OK;

public class ListMovieByCrewAsyncTask extends GenericAsyncTask<Void, Void, List<Movie>> {

    private CrewResource crewResource;
    private Person person;

    public ListMovieByCrewAsyncTask(Context context, CrewResource crewResource, Person person) {
        super(context);
        this.crewResource = crewResource;
        this.person = person;
    }

    @Override
    protected AsyncTaskResult<List<Movie>> doInBackground(Void... params) {

        try {
            Response<List<Movie>> response = crewResource.listMovieByCrew(person.getId(), getApiKey(), Locale.getDefault().getLanguage()).execute();
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
