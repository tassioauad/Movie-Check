package com.tassioauad.moviecheck.model.api.resource;


import com.tassioauad.moviecheck.model.entity.Genre;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface GenreResource {

    @GET("genre/movie/list")
    Call<List<Genre>> listAllofMovie(@Query("api_key") String apiKey);

}
