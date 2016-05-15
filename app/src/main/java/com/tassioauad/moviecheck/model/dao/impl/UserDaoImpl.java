package com.tassioauad.moviecheck.model.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.model.dao.Dao;
import com.tassioauad.moviecheck.model.dao.UserDao;
import com.tassioauad.moviecheck.model.entity.User;

public class UserDaoImpl extends Dao implements UserDao {

    public static final String TABLE_NAME = "user";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_GOOGLEID = "google_id";
    public static final String COLUMN_NAME_FULLNAME = "fullname";
    public static final String COLUMN_NAME_EMAIL = "email";
    public static final String COLUMN_NAME_PHOTOURL = "photourl";
    public static final String[] COLUMNS = new String[]{COLUMN_NAME_ID, COLUMN_NAME_GOOGLEID, COLUMN_NAME_FULLNAME,
            COLUMN_NAME_EMAIL, COLUMN_NAME_PHOTOURL};

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
            COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
            COLUMN_NAME_GOOGLEID + " TEXT, \n" +
            COLUMN_NAME_FULLNAME + " TEXT, \n" +
            COLUMN_NAME_EMAIL + " TEXT, \n" +
            COLUMN_NAME_PHOTOURL + " TEXT \n" +
            ")";

    public UserDaoImpl(Context context, SQLiteDatabase database) {
        super(context, database);
    }

    @Override
    public void save(User user) {
        if (user.getId() == null) {
            insert(user);
        } else {
            update(user);
        }
    }

    @Override
    public void update(User user) {
        getDatabase().update(TABLE_NAME, toContentValues(user), COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(user.getId())});
    }

    @Override
    public void insert(User user) {
        long id = getDatabase().insert(TABLE_NAME, null, toContentValues(user));
        user.setId(id);
    }

    @Override
    public void login(User user) {
        save(user);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getContext().getString(R.string.sharedpreferences), Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(getContext().getString(R.string.sharedpreferences_loggeduser), new Gson().toJson(user))
                .apply();
    }

    @Override
    public User getLoggedUser() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getContext().getString(R.string.sharedpreferences), Context.MODE_PRIVATE);
        return new Gson().fromJson(sharedPreferences.getString(getContext().getString(R.string.sharedpreferences_loggeduser), null), User.class);
    }

    @Override
    public void logout() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getContext().getString(R.string.sharedpreferences), Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(getContext().getString(R.string.sharedpreferences_loggeduser), null)
                .apply();
    }

    @Override
    public User findById(Integer id) {
        Cursor cursor = getDatabase().query(TABLE_NAME, COLUMNS, COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(id)},
                null, null, null, null);

        return fromCursor(cursor);
    }

    public User fromCursor(Cursor cursor) {
       if( cursor.moveToNext()) {
           User user = new User();
           user.setId(cursor.getLong(0));
           user.setGoogleId(cursor.getString(1));
           user.setName(cursor.getString(2));
           user.setEmail(cursor.getString(3));
           user.setPhotoUrl(cursor.getString(4));

           return user;
       }

        return null;
    }

    public ContentValues toContentValues(User user) {
        ContentValues contentValues = new ContentValues();
        if (user.getId() != null) {
            contentValues.put(COLUMN_NAME_ID, user.getId());
        }
        contentValues.put(COLUMN_NAME_GOOGLEID, user.getGoogleId());
        contentValues.put(COLUMN_NAME_FULLNAME, user.getName());
        contentValues.put(COLUMN_NAME_EMAIL, user.getEmail());
        contentValues.put(COLUMN_NAME_PHOTOURL, user.getPhotoUrl());

        return contentValues;
    }

}
