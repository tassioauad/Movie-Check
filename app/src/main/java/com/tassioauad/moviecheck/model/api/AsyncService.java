package com.tassioauad.moviecheck.model.api;

import com.tassioauad.moviecheck.model.api.asynctask.ApiResultListener;

public interface AsyncService {

    ApiResultListener getApiResultListener();

    void setApiResultListener(ApiResultListener listener);

    void cancelAllService();
}
