package com.tassioauad.moviecheck.model.api.resource;


import com.tassioauad.moviecheck.model.entity.Video;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface VideoResource {

    @GET("movie/{id}/videos")
    Call<List<Video>> listAllByMovie(@Path("id") Long movieId, @Query("api_key") String apiKey);

}
