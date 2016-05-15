package com.tassioauad.moviecheck.model.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tassioauad.moviecheck.model.dao.Dao;
import com.tassioauad.moviecheck.model.dao.MovieDao;
import com.tassioauad.moviecheck.model.dao.MovieInterestDao;
import com.tassioauad.moviecheck.model.dao.UserDao;
import com.tassioauad.moviecheck.model.entity.MovieInterest;

import java.util.ArrayList;
import java.util.List;

public class MovieInterestDaoImpl extends Dao implements MovieInterestDao {

    private static final String TABLE_NAME = "movie_interest";
    private static final String COLUMN_NAME_ID = "id";
    private static final String COLUMN_NAME_USER_ID = "user_id";
    private static final String COLUMN_NAME_MOVIE_ID = "movie_id";
    private static final String[] COLUMNS = new String[]{COLUMN_NAME_ID, COLUMN_NAME_USER_ID, COLUMN_NAME_MOVIE_ID};

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
            COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
            COLUMN_NAME_USER_ID + " TEXT, \n" +
            COLUMN_NAME_MOVIE_ID + " TEXT \n" +
            ")";

    private MovieDao movieDao;
    private UserDao userDao;

    public MovieInterestDaoImpl(Context context, SQLiteDatabase database, MovieDao movieDao, UserDao userDao) {
        super(context, database);
        this.movieDao = movieDao;
        this.userDao = userDao;
    }

    @Override
    public List<MovieInterest> listAll() {
        Cursor cursor = getDatabase().query(TABLE_NAME, COLUMNS, null, null, null, null, COLUMN_NAME_ID + " ASC");

        return fromCursor(cursor);
    }

    @Override
    public void insert(MovieInterest movieInterest) {
        getDatabase().insert(TABLE_NAME, null, toContentValues(movieInterest));
    }

    public ContentValues toContentValues(MovieInterest movieInterest) {
        ContentValues contentValues = new ContentValues();
        if(movieInterest.getId() != null) {
            contentValues.put(COLUMN_NAME_ID, movieInterest.getId());
        }
        movieDao.save(movieInterest.getMovie());
        contentValues.put(COLUMN_NAME_MOVIE_ID, movieInterest.getMovie().getId());
        contentValues.put(COLUMN_NAME_USER_ID, movieInterest.getUser().getId());
        return contentValues;
    }

    public List<MovieInterest> fromCursor(Cursor cursor) {
        List<MovieInterest> movieInterestList = new ArrayList<>();

        while (cursor.moveToNext()) {
            MovieInterest movieInterest = new MovieInterest();
            movieInterest.setId(cursor.getInt(0));
            movieInterest.setUser(userDao.findById(cursor.getInt(1)));
            movieInterest.setMovie(movieDao.findById(cursor.getLong(2)));

            movieInterestList.add(movieInterest);
        }

        return movieInterestList;
    }

}
