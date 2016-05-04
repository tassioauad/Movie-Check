package com.tassioauad.moviecheck.dagger;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.model.api.CastApi;
import com.tassioauad.moviecheck.model.api.CrewApi;
import com.tassioauad.moviecheck.model.api.GenreApi;
import com.tassioauad.moviecheck.model.api.ItemTypeAdapterFactory;
import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.model.api.ReviewApi;
import com.tassioauad.moviecheck.model.api.VideoApi;
import com.tassioauad.moviecheck.model.api.impl.CastApiImpl;
import com.tassioauad.moviecheck.model.api.impl.CrewApiImpl;
import com.tassioauad.moviecheck.model.api.impl.GenreApiImpl;
import com.tassioauad.moviecheck.model.api.impl.MovieApiImpl;
import com.tassioauad.moviecheck.model.api.impl.ReviewApiImpl;
import com.tassioauad.moviecheck.model.api.impl.VideoApiImpl;
import com.tassioauad.moviecheck.model.api.resource.CastResource;
import com.tassioauad.moviecheck.model.api.resource.CrewResource;
import com.tassioauad.moviecheck.model.api.resource.GenreResource;
import com.tassioauad.moviecheck.model.api.resource.MovieResource;
import com.tassioauad.moviecheck.model.api.resource.ReviewResource;
import com.tassioauad.moviecheck.model.api.resource.VideoResource;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

@Module(library = true, includes = AppModule.class)
public class ApiModule {

    @Provides
    public Retrofit provideRetrofit(Context context) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .setDateFormat("yyyy'-'MM'-'dd")
                .create();

        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.themoviedbapi_baseurl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    public MovieResource provideMovieResource(Context context) {
        return provideRetrofit(context).create(MovieResource.class);
    }

    @Provides
    public VideoResource provideVideoResource(Context context) {
        return provideRetrofit(context).create(VideoResource.class);
    }

    @Provides
    public ReviewResource provideReviewResource(Context context) {
        return provideRetrofit(context).create(ReviewResource.class);
    }

    @Provides
    public CastResource provideCastResource(Context context) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory("cast"))
                .setDateFormat("yyyy'-'MM'-'dd")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.themoviedbapi_baseurl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(CastResource.class);
    }

    @Provides
    public CrewResource provideCrewResource(Context context) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory("crew"))
                .setDateFormat("yyyy'-'MM'-'dd")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.themoviedbapi_baseurl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(CrewResource.class);
    }

    @Provides
    public GenreResource provideGenreResource(Context context) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory("genres"))
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.themoviedbapi_baseurl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(GenreResource.class);
    }

    @Provides
    public MovieApi provideMovieApi(Context context) {
        return new MovieApiImpl(context, provideMovieResource(context));
    }

    @Provides
    public CastApi provideCastApi(Context context) {
        return new CastApiImpl(context, provideCastResource(context));
    }

    @Provides
    public CrewApi provideCrewApi(Context context) {
        return new CrewApiImpl(context, provideCrewResource(context));
    }

    @Provides
    public GenreApi provideGenreApi(Context context) {
        return new GenreApiImpl(context, provideGenreResource(context));
    }

    @Provides
    public ReviewApi provideReviewApi(Context context) {
        return new ReviewApiImpl(context, provideReviewResource(context));
    }

    @Provides
    public VideoApi provideVideoApi(Context context) {
        return new VideoApiImpl(context, provideVideoResource(context));
    }
}
