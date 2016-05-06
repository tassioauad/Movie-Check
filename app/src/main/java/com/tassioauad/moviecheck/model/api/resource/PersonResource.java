package com.tassioauad.moviecheck.model.api.resource;


import com.tassioauad.moviecheck.model.entity.Person;
import com.tassioauad.moviecheck.model.entity.Video;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface PersonResource {

    @GET("person/{id}")
    Call<Person> findById(@Path("id") Long personId, @Query("api_key") String apiKey);

}
