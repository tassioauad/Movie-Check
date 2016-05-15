package com.tassioauad.moviecheck.model.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tassioauad.moviecheck.model.dao.Dao;
import com.tassioauad.moviecheck.model.dao.MovieDao;
import com.tassioauad.moviecheck.model.entity.Movie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieDaoImpl extends Dao implements MovieDao {

    private Long id;
    private String title;
    private Date releaseDate;
    private String backdropUrl;
    private String posterUrl;
    private String overview;
    private boolean adult;
    private float voteAverage;
    private long voteCount;
    private String language;
    private double popularity;
    
    private static final String TABLE_NAME = "movie";
    private static final String COLUMN_NAME_ID = "id";
    private static final String COLUMN_NAME_TITLE = "title";
    private static final String COLUMN_NAME_RELEASE_DATE = "release_date";
    private static final String COLUMN_NAME_BACKDROP_URL = "backdrop_url";
    private static final String COLUMN_NAME_POSTER_URL = "poster_url";
    private static final String[] COLUMNS = new String [] {COLUMN_NAME_ID, COLUMN_NAME_TITLE,
            COLUMN_NAME_RELEASE_DATE, COLUMN_NAME_BACKDROP_URL, COLUMN_NAME_POSTER_URL};

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
            COLUMN_NAME_ID + " INTEGER PRIMARY KEY, \n" +
            COLUMN_NAME_TITLE + " TEXT, \n" +
            COLUMN_NAME_RELEASE_DATE + " INTEGER, \n" +
            COLUMN_NAME_BACKDROP_URL + " TEXT, \n" +
            COLUMN_NAME_POSTER_URL + " TEXT \n" +
            ")";

    
    public MovieDaoImpl(Context context, SQLiteDatabase database) {
        super(context, database);
    }

    @Override
    public void save(Movie movie) {
        if(movie.getId() == null) {
            insert(movie);
        } else {
            update(movie);
        }
    }

    @Override
    public void update(Movie movie) {
        getDatabase().update(TABLE_NAME, toContentValues(movie), COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(movie.getId())});
    }

    @Override
    public void insert(Movie movie) {
        getDatabase().insert(TABLE_NAME, null, toContentValues(movie));
    }

    @Override
    public Movie findById(Long id) {
        Cursor cursor = getDatabase().query(TABLE_NAME, COLUMNS, COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(id)},
                null, null, null);

        List<Movie> movieList = fromCursor(cursor);
        if(movieList.size() == 0) {
            return null;
        }

        return movieList.get(0);
    }

    @Override
    public List<Movie> listAll() {
        Cursor cursor = getDatabase().query(TABLE_NAME, COLUMNS, null, null, null, null, COLUMN_NAME_ID + " ASC");

        return fromCursor(cursor);
    }

    public List<Movie> fromCursor(Cursor cursor) {
        List<Movie> movieList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Movie movie = new Movie();
            movie.setId(cursor.getLong(0));
            movie.setTitle(cursor.getString(1));
            movie.setReleaseDate(new Date(cursor.getLong(2)));
            movie.setBackdropUrl(cursor.getString(3));
            movie.setPosterUrl(cursor.getString(4));

            movieList.add(movie);
        }

        return movieList;
    }

    public ContentValues toContentValues(Movie movie) {
        ContentValues contentValues = new ContentValues();
        if(movie.getId() != null) {
            contentValues.put(COLUMN_NAME_ID, movie.getId());
        }
        contentValues.put(COLUMN_NAME_TITLE, movie.getTitle());
        contentValues.put(COLUMN_NAME_RELEASE_DATE, movie.getReleaseDate().getTime());
        contentValues.put(COLUMN_NAME_BACKDROP_URL, movie.getBackdropUrl());
        contentValues.put(COLUMN_NAME_POSTER_URL, movie.getPosterUrl());


        return contentValues;
    }
}
