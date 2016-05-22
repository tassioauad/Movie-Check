package com.tassioauad.moviecheck.model.api;

import android.content.Context;

import com.tassioauad.moviecheck.model.api.asynctask.ApiResultListener;


public abstract class GenericApi implements AsyncService {

    private Context context;

    private ApiResultListener listener;

    public GenericApi(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public ApiResultListener getApiResultListener() {
        return listener;
    }

    public void setApiResultListener(ApiResultListener listener) {
        this.listener = listener;
    }

    public void verifyServiceResultListener() {
        if (getApiResultListener() == null) {
            throw new RuntimeException("Before invoke this method, you must set an ApiResultListener instance");
        }
    }

}
