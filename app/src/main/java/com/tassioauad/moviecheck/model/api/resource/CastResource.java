package com.tassioauad.moviecheck.model.api.resource;


import com.tassioauad.moviecheck.model.entity.Cast;
import com.tassioauad.moviecheck.model.entity.Movie;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface CastResource {

    @GET("movie/{id}/credits")
    Call<List<Cast>> listAllByMovie(@Path("id") Long id, @Query("api_key") String apiKey);

}
