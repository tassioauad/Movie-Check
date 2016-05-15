package com.tassioauad.moviecheck.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Dao {

    private Context context;
    private SQLiteDatabase database;

    public Dao(Context context, SQLiteDatabase database) {
        this.context = context;
        this.database = database;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public Context getContext() {
        return context;
    }
}
