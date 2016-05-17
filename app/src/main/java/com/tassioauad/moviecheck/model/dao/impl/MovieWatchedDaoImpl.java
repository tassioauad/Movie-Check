package com.tassioauad.moviecheck.model.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tassioauad.moviecheck.model.dao.Dao;
import com.tassioauad.moviecheck.model.dao.MovieDao;
import com.tassioauad.moviecheck.model.dao.MovieWatchedDao;
import com.tassioauad.moviecheck.model.dao.UserDao;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.MovieWatched;
import com.tassioauad.moviecheck.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class MovieWatchedDaoImpl extends Dao implements MovieWatchedDao{

    public static final String TABLE_NAME = "movie_watched";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_USER_ID = "user_id";
    public static final String COLUMN_NAME_MOVIE_ID = "movie_id";
    public static final String COLUMN_NAME_VOTE = "vote";
    public static final String[] COLUMNS = new String[]{COLUMN_NAME_ID, COLUMN_NAME_USER_ID,
            COLUMN_NAME_MOVIE_ID, COLUMN_NAME_VOTE};

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
            COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
            COLUMN_NAME_USER_ID + " INTEGER, \n" +
            COLUMN_NAME_MOVIE_ID + " INTEGER, \n" +
            COLUMN_NAME_VOTE + " REAL, \n" +
            "FOREIGN KEY(" + COLUMN_NAME_USER_ID + ") REFERENCES "+UserDaoImpl.TABLE_NAME+"("+UserDaoImpl.COLUMN_NAME_ID+"), \n" +
            "FOREIGN KEY(" + COLUMN_NAME_MOVIE_ID + ") REFERENCES "+MovieDaoImpl.TABLE_NAME+"("+MovieDaoImpl.COLUMN_NAME_ID+") \n" +
            ")";

    private MovieDao movieDao;
    private UserDao userDao;

    public MovieWatchedDaoImpl(Context context, SQLiteDatabase database, MovieDao movieDao, UserDao userDao) {
        super(context, database);
        this.movieDao = movieDao;
        this.userDao = userDao;
    }

    @Override
    public List<MovieWatched> listAll(User user) {
        Cursor cursor = getDatabase().query(TABLE_NAME, COLUMNS, COLUMN_NAME_USER_ID + " = ?",
                new String[]{String.valueOf(String.valueOf(user.getId()))}, null, null, null);

        return fromCursor(cursor);
    }

    @Override
    public MovieWatched findByMovie(Movie movie, User user) {
        Cursor cursor = getDatabase().query(TABLE_NAME, COLUMNS, COLUMN_NAME_MOVIE_ID + " = ? AND " + COLUMN_NAME_USER_ID + " = ?",
                new String[]{String.valueOf(movie.getId()), String.valueOf(user.getId())}, null, null, null);

        List<MovieWatched> movieWatchedList = fromCursor(cursor);
        if (movieWatchedList.size() == 0) {
            return null;
        }

        return movieWatchedList.get(0);
    }

    @Override
    public void remove(MovieWatched movieWatched) {
        getDatabase().delete(TABLE_NAME, COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(movieWatched.getId())});
    }

    @Override
    public void insert(MovieWatched movieWatched) {
        long id = getDatabase().insert(TABLE_NAME, null, toContentValues(movieWatched));
        movieWatched.setId(id);
    }

    @Override
    public void update(MovieWatched movieWatched) {
        long id = getDatabase().update(TABLE_NAME, toContentValues(movieWatched), COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(movieWatched.getId())});
        movieWatched.setId(id);
    }

    @Override
    public void save(MovieWatched movieWatched) {
        if(movieWatched.getId() == null) {
            insert(movieWatched);
        } else {
            update(movieWatched);
        }
    }

    public ContentValues toContentValues(MovieWatched movieWatched) {
        ContentValues contentValues = new ContentValues();
        if (movieWatched.getId() != null) {
            contentValues.put(COLUMN_NAME_ID, movieWatched.getId());
        }
        movieDao.save(movieWatched.getMovie());
        contentValues.put(COLUMN_NAME_MOVIE_ID, movieWatched.getMovie().getId());
        contentValues.put(COLUMN_NAME_USER_ID, movieWatched.getUser().getId());
        contentValues.put(COLUMN_NAME_VOTE, movieWatched.getVote());

        return contentValues;
    }

    public List<MovieWatched> fromCursor(Cursor cursor) {
        List<MovieWatched> movieWatchedList = new ArrayList<>();

        while (cursor.moveToNext()) {
            MovieWatched movieWatched = new MovieWatched();
            movieWatched.setId(cursor.getLong(0));
            movieWatched.setUser(userDao.findById(cursor.getInt(1)));
            movieWatched.setMovie(movieDao.findById(cursor.getLong(2)));
            movieWatched.setVote(cursor.getFloat(3));

            movieWatchedList.add(movieWatched);
        }

        return movieWatchedList;
    }

}
