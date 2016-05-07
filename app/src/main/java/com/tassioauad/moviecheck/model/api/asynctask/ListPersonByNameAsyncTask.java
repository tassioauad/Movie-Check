package com.tassioauad.moviecheck.model.api.asynctask;

import android.content.Context;

import com.tassioauad.moviecheck.model.api.asynctask.exception.BadRequestException;
import com.tassioauad.moviecheck.model.api.resource.PersonResource;
import com.tassioauad.moviecheck.model.entity.Person;

import java.util.List;

import retrofit.Response;

import static java.net.HttpURLConnection.HTTP_OK;

public class ListPersonByNameAsyncTask extends GenericAsyncTask<Void, Void, List<Person>> {

    private PersonResource personResource;
    private String name;
    private Integer page;

    public ListPersonByNameAsyncTask(Context context, PersonResource personResource, String name, Integer page) {
        super(context);
        this.personResource = personResource;
        this.name = name;
        this.page = page;
    }

    @Override
    protected AsyncTaskResult<List<Person>> doInBackground(Void... params) {

        try {
            Response<List<Person>> response = personResource.listByName(getApiKey(), name, page).execute();
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
