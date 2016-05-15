package com.tassioauad.moviecheck.dagger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tassioauad.moviecheck.model.SqliteConnection;
import com.tassioauad.moviecheck.model.dao.UserDao;
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
}
