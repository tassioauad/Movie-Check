package com.tassioauad.moviecheck.dagger;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.model.api.CastApi;
import com.tassioauad.moviecheck.model.api.CastTypeAdapterFactory;
import com.tassioauad.moviecheck.model.api.CrewApi;
import com.tassioauad.moviecheck.model.api.CrewTypeAdapterFactory;
import com.tassioauad.moviecheck.model.api.ItemTypeAdapterFactory;
import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.model.api.impl.CastApiImpl;
import com.tassioauad.moviecheck.model.api.impl.CrewApiImpl;
import com.tassioauad.moviecheck.model.api.impl.MovieApiImpl;
import com.tassioauad.moviecheck.model.api.resource.CastResource;
import com.tassioauad.moviecheck.model.api.resource.CrewResource;
import com.tassioauad.moviecheck.model.api.resource.MovieResource;

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

    public Retrofit provideRetrofitForCastResource(Context context) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new CastTypeAdapterFactory())
                .setDateFormat("yyyy'-'MM'-'dd")
                .create();

        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.themoviedbapi_baseurl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public Retrofit provideRetrofitForCrewResource(Context context) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new CrewTypeAdapterFactory())
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
    public CastResource provideCastResource(Context context) {
        return provideRetrofitForCastResource(context).create(CastResource.class);
    }

    @Provides
    public CrewResource provideCrewResource(Context context) {
        return provideRetrofitForCrewResource(context).create(CrewResource.class);
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
}
