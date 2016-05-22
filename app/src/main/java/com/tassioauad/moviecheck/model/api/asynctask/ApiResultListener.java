package com.tassioauad.moviecheck.model.api.asynctask;

public interface ApiResultListener {

    void onResult(Object object);

    void onException(Exception exception);

}
