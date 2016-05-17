package com.tassioauad.moviecheck.model.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tassioauad.moviecheck.model.dao.Dao;
import com.tassioauad.moviecheck.model.dao.MovieDao;
import com.tassioauad.moviecheck.model.entity.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MovieDaoImpl extends Dao implements MovieDao {

    public static final String TABLE_NAME = "movie";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_RELEASE_DATE = "release_date";
    public static final String COLUMN_NAME_BACKDROP_URL = "backdrop_url";
    public static final String COLUMN_NAME_POSTER_URL = "poster_url";
    public static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
    public static final String COLUMN_NAME_VOTE_COUNT = "vote_count";
    public static final String COLUMN_NAME_VOTE_OVERVIEW = "overview";
    public static final String COLUMN_NAME_GENRE_IDS = "genre_ids";
    private static final String GENRE_ID_DIVISOR = ";";
    public static final String[] COLUMNS = new String [] {COLUMN_NAME_ID, COLUMN_NAME_TITLE,
            COLUMN_NAME_RELEASE_DATE, COLUMN_NAME_BACKDROP_URL, COLUMN_NAME_POSTER_URL,
            COLUMN_NAME_VOTE_AVERAGE, COLUMN_NAME_VOTE_COUNT, COLUMN_NAME_VOTE_OVERVIEW,
            COLUMN_NAME_GENRE_IDS};

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
            COLUMN_NAME_ID + " INTEGER PRIMARY KEY, \n" +
            COLUMN_NAME_TITLE + " TEXT, \n" +
            COLUMN_NAME_RELEASE_DATE + " INTEGER, \n" +
            COLUMN_NAME_BACKDROP_URL + " TEXT, \n" +
            COLUMN_NAME_POSTER_URL + " TEXT, \n" +
            COLUMN_NAME_VOTE_AVERAGE + " REAL, \n" +
            COLUMN_NAME_VOTE_COUNT + " INTEGER, \n" +
            COLUMN_NAME_VOTE_OVERVIEW + " TEXT, \n" +
            COLUMN_NAME_GENRE_IDS + " TEXT \n" +
            ")";

    
    public MovieDaoImpl(Context context, SQLiteDatabase database) {
        super(context, database);
    }

    @Override
    public void save(Movie movie) {
        if(findById(movie.getId()) == null) {
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

    @Override
    public List<Movie> listAllUpcoming() {
        Cursor cursor = getDatabase().query(TABLE_NAME, COLUMNS, COLUMN_NAME_RELEASE_DATE + " > ?", new String[]{String.valueOf(new Date().getTime())}, null, null, COLUMN_NAME_ID + " ASC");

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
            movie.setVoteAverage(cursor.getFloat(5));
            movie.setVoteCount(cursor.getLong(6));
            movie.setOverview(cursor.getString(7));
            ArrayList<Long> genreIdList = new ArrayList<>();
            String[] genreIdArray = cursor.getString(8).split(GENRE_ID_DIVISOR);
            for(String genreId : genreIdArray) {
                if(!genreId.isEmpty()) {
                    genreIdList.add(Long.parseLong(genreId));
                }
            }
            movie.setGenreId(genreIdList);

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
        contentValues.put(COLUMN_NAME_VOTE_AVERAGE, movie.getVoteAverage());
        contentValues.put(COLUMN_NAME_VOTE_COUNT, movie.getVoteCount());
        contentValues.put(COLUMN_NAME_VOTE_OVERVIEW, movie.getOverview());
        StringBuilder stringBuilder = new StringBuilder();
        for(Long genreId : movie.getGenreId()) {
            stringBuilder.append(genreId);
            stringBuilder.append(GENRE_ID_DIVISOR);
        }
        contentValues.put(COLUMN_NAME_GENRE_IDS, stringBuilder.toString());

        return contentValues;
    }
}
