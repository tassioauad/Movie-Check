package com.tassioauad.moviecheck.dagger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tassioauad.moviecheck.model.SqliteConnection;
import com.tassioauad.moviecheck.model.dao.MovieDao;
import com.tassioauad.moviecheck.model.dao.MovieInterestDao;
import com.tassioauad.moviecheck.model.dao.MovieWatchedDao;
import com.tassioauad.moviecheck.model.dao.UserDao;
import com.tassioauad.moviecheck.model.dao.impl.MovieDaoImpl;
import com.tassioauad.moviecheck.model.dao.impl.MovieInterestDaoImpl;
import com.tassioauad.moviecheck.model.dao.impl.MovieWatchedDaoImpl;
import com.tassioauad.moviecheck.model.dao.impl.UserDaoImpl;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = AppModule.class)
public class DaoModule {

    @Provides
    public SQLiteDatabase provideSqLiteDatabase(Context context) {
        return new SqliteConnection(context).getWritableDatabase();
    }

    @Provides
    public UserDao provideUserDao(Context context) {
        return new UserDaoImpl(context, provideSqLiteDatabase(context));
    }

    @Provides
    public MovieDao provideMovieDao(Context context) {
        return new MovieDaoImpl(context, provideSqLiteDatabase(context));
    }

    @Provides
    public MovieInterestDao provideMovieInterestDao(Context context) {
        return new MovieInterestDaoImpl(context, provideSqLiteDatabase(context),
                provideMovieDao(context), provideUserDao(context));
    }

    @Provides
    public MovieWatchedDao provideMovieWatchedDao(Context context) {
        return new MovieWatchedDaoImpl(context, provideSqLiteDatabase(context),
                provideMovieDao(context), provideUserDao(context));
    }
}
