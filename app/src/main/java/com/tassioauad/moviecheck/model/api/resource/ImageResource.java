package com.tassioauad.moviecheck.model.api.resource;

import com.tassioauad.moviecheck.model.entity.Image;
import com.tassioauad.moviecheck.model.entity.Review;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ImageResource {

    @GET("movie/{id}/images")
    Call<List<Image>> listByMovie(@Path("id") Long movieId, @Query("api_key") String apiKey);

}
