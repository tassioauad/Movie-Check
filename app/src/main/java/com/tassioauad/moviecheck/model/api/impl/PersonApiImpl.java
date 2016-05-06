package com.tassioauad.moviecheck.model.api.impl;

import android.content.Context;
import android.os.AsyncTask;

import com.tassioauad.moviecheck.model.api.GenericApi;
import com.tassioauad.moviecheck.model.api.PersonApi;
import com.tassioauad.moviecheck.model.api.asynctask.FindPersonAsyncTask;
import com.tassioauad.moviecheck.model.api.resource.PersonResource;

public class PersonApiImpl extends GenericApi implements PersonApi {

    private FindPersonAsyncTask findPersonAsyncTask;
    private PersonResource personResource;

    public PersonApiImpl(Context context, PersonResource personResource) {
        super(context);
        this.personResource = personResource;
    }

    @Override
    public void findById(Long personId) {
        verifyServiceResultListener();
        findPersonAsyncTask = new FindPersonAsyncTask(getContext(), personResource, personId);
        findPersonAsyncTask.setApiResultListener(getApiResultListener());
        findPersonAsyncTask.execute();
    }

    @Override
    public void cancelAllServices() {
        if(findPersonAsyncTask != null && findPersonAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            findPersonAsyncTask.cancel(true);
        }
    }
}
