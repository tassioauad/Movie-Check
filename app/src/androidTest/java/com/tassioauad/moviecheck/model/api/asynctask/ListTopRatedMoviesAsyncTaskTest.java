package com.tassioauad.moviecheck.model.api.asynctask;

import android.test.AndroidTestCase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.model.api.ItemTypeAdapterFactory;
import com.tassioauad.moviecheck.model.api.resource.MovieResource;
import com.tassioauad.moviecheck.model.entity.Movie;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ListTopRatedMoviesAsyncTaskTest extends AndroidTestCase {

    MovieResource movieResource;
    int page = 1;

    public void setUp() throws Exception {
        super.setUp();
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory(rootName))
                .setDateFormat("yyyy'-'MM'-'dd")
                .create();

        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl(getContext().getString(R.string.themoviedbapi_baseurl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        movieResource = retrofit.create(MovieResource.class);
    }

    public void testDoInBackground_Success() throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        ListTopRatedMoviesAsyncTask listTopRatedMoviesAsyncTask =
                new ListTopRatedMoviesAsyncTask(getContext(), movieResource, page);

        listTopRatedMoviesAsyncTask.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                assertNotNull(object);
                assertTrue(((List<Movie>) object).size() > 0);
                countDownLatch.countDown();
            }

            @Override
            public void onException(Exception exception) {
                fail("Exception has happened");
                countDownLatch.countDown();
            }
        });

        listTopRatedMoviesAsyncTask.execute();
        countDownLatch.await();
    }
}