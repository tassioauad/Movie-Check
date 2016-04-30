package com.tassioauad.moviecheck.model.api.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.tassioauad.moviecheck.R;

public abstract class GenericAsyncTask<PARAM, POGRESS, RETURN> extends AsyncTask<PARAM, POGRESS, AsyncTaskResult<RETURN>> {

    private Context context;
    private ApiResultListener apiResultListener;

    public GenericAsyncTask(Context context) {
        this.context = context;
    }

    public void setApiResultListener(ApiResultListener listener) {
        this.apiResultListener = listener;
    }

    public Context getContext() {
        return context;
    }

    public String getBaseUrl() {
        return context.getString(R.string.themoviedbapi_baseurl);
    }

    public String getApiKey() {
        return context.getString(R.string.themoviedbapi_key);
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<RETURN> returnAsyncTaskResult) {
        if (returnAsyncTaskResult.getResult() != null) {
            apiResultListener.onResult(returnAsyncTaskResult.getResult());
        } else if (returnAsyncTaskResult.getError() != null) {
            apiResultListener.onException(returnAsyncTaskResult.getError());
        } else {
            apiResultListener.onResult(returnAsyncTaskResult.getResult());
        }
    }


}