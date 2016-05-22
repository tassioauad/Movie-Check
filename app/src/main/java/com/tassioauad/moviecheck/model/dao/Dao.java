package com.tassioauad.moviecheck.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;

public class Dao implements DaoLoader {

    private Context context;
    private SQLiteDatabase database;
    private DaoListener daoListener;
    private FragmentActivity activity;

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

    @Override
    public void setDaoListener(DaoListener daoListener) {
        this.daoListener = daoListener;
    }

    @Override
    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public DaoListener getDaoListener() {
        return daoListener;
    }

    public FragmentActivity getActivity() {
        return activity;
    }

    public void checkDaoListener() {
        if(daoListener == null) {
            throw new RuntimeException("Your presenter must to set a DaoListener instance on Dao class");
        }
    }
}
