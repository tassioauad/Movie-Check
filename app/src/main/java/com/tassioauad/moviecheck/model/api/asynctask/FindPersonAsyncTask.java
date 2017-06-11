package com.tassioauad.moviecheck.model.api.asynctask;

import android.content.Context;

import com.tassioauad.moviecheck.model.api.asynctask.exception.BadRequestException;
import com.tassioauad.moviecheck.model.api.resource.PersonResource;
import com.tassioauad.moviecheck.model.entity.Person;

import java.util.Locale;

import retrofit.Response;

import static java.net.HttpURLConnection.HTTP_OK;

public class FindPersonAsyncTask extends GenericAsyncTask<Void, Void, Person> {

    private PersonResource personResource;
    private Long personId;

    public FindPersonAsyncTask(Context context, PersonResource personResource, Long personId) {
        super(context);
        this.personResource = personResource;
        this.personId = personId;
    }

    @Override
    protected AsyncTaskResult<Person> doInBackground(Void... params) {

        try {
            Response<Person> response = personResource.findById(personId, getApiKey(), Locale.getDefault().getLanguage()).execute();
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
