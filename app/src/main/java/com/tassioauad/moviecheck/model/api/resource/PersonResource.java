package com.tassioauad.moviecheck.model.api.resource;


import com.tassioauad.moviecheck.model.entity.Person;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface PersonResource {

    @GET("person/{id}")
    Call<Person> findById(@Path("id") Long personId, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("search/person")
    Call<List<Person>> listByName(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page, @Query("language") String language);


}
