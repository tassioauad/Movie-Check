package com.tassioauad.moviecheck.model.api.resource;


import com.tassioauad.moviecheck.model.entity.Crew;
import com.tassioauad.moviecheck.model.entity.Movie;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface CrewResource {

    @GET("movie/{id}/credits")
    Call<List<Crew>> listAllByMovie(@Path("id") Long id, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("person/{id}/movie_credits")
    Call<List<Movie>> listMovieByCrew(@Path("id") Long personId, @Query("api_key") String apiKey, @Query("language") String language);
}
