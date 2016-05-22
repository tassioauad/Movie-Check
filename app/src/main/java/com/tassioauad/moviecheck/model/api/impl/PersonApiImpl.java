package com.tassioauad.moviecheck.model.api.impl;

import android.content.Context;
import android.os.AsyncTask;

import com.tassioauad.moviecheck.model.api.GenericApi;
import com.tassioauad.moviecheck.model.api.PersonApi;
import com.tassioauad.moviecheck.model.api.asynctask.FindPersonAsyncTask;
import com.tassioauad.moviecheck.model.api.asynctask.ListPersonByNameAsyncTask;
import com.tassioauad.moviecheck.model.api.resource.PersonResource;

public class PersonApiImpl extends GenericApi implements PersonApi {

    private FindPersonAsyncTask findPersonAsyncTask;
    private ListPersonByNameAsyncTask listPersonByNameAsyncTask;
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
    public void listByName(String name, int page) {
        verifyServiceResultListener();
        listPersonByNameAsyncTask = new ListPersonByNameAsyncTask(getContext(), personResource, name, page);
        listPersonByNameAsyncTask.setApiResultListener(getApiResultListener());
        listPersonByNameAsyncTask.execute();
    }

    @Override
    public void listByName(String name) {
        verifyServiceResultListener();
        listPersonByNameAsyncTask = new ListPersonByNameAsyncTask(getContext(), personResource, name, 1);
        listPersonByNameAsyncTask.setApiResultListener(getApiResultListener());
        listPersonByNameAsyncTask.execute();
    }

    @Override
    public void cancelAllServices() {
        if (findPersonAsyncTask != null && findPersonAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            findPersonAsyncTask.cancel(true);
        }
        if (listPersonByNameAsyncTask != null && listPersonByNameAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            listPersonByNameAsyncTask.cancel(true);
        }
    }
}
