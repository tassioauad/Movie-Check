package com.tassioauad.moviecheck.model.dao.impl;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.tassioauad.moviecheck.model.SqliteConnection;

public class MovieCheckContentProvider extends ContentProvider {

    public static final String PROVIDER_NAME = "com.tassioauad.moviecheck.model.dao.contentprovider.moviecheckcontentprovider";

    private SQLiteDatabase database;
    private static final UriMatcher uriMatcher;
    public static final String PATH_MOVIE = "movie";
    public static final String PATH_USER = "user";
    public static final String PATH_MOVIE_WATCHED = "moviewatched";
    public static final String PATH_MOVIE_INTEREST = "movieinterest";
    public static final String PATH_MOVIE_NOT_INTEREST = "movienotinterest";
    private static final int CODE_MOVIE = 1;
    private static final int CODE_USER = 2;
    private static final int CODE_MOVIE_WATCHED = 3;
    private static final int CODE_MOVIE_INTEREST = 4;
    private static final int CODE_MOVIE_NOT_INTEREST = 5;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, PATH_MOVIE, CODE_MOVIE);
        uriMatcher.addURI(PROVIDER_NAME, PATH_USER, CODE_USER);
        uriMatcher.addURI(PROVIDER_NAME, PATH_MOVIE_WATCHED, CODE_MOVIE_WATCHED);
        uriMatcher.addURI(PROVIDER_NAME, PATH_MOVIE_INTEREST, CODE_MOVIE_INTEREST);
        uriMatcher.addURI(PROVIDER_NAME, PATH_MOVIE_NOT_INTEREST, CODE_MOVIE_NOT_INTEREST);
    }

    @Override
    public boolean onCreate() {
        database = new SqliteConnection(getContext()).getWritableDatabase();
        return database != null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case CODE_MOVIE:
                return database.query(MovieDaoImpl.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            case CODE_USER:
                return database.query(UserDaoImpl.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            case CODE_MOVIE_INTEREST:
                return database.query(MovieInterestDaoImpl.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            case CODE_MOVIE_NOT_INTEREST:
                return database.query(MovieNotInterestDaoImpl.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            case CODE_MOVIE_WATCHED:
                return database.query(MovieWatchedDaoImpl.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        }
        throw new IllegalArgumentException("Unsupported URI for insertion: " + uri);
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case CODE_MOVIE:
                return "vnd.android.cursor.dir/vnd.tassioauad.moviecheck.model.entity.movie";
            case CODE_USER:
                return "vnd.android.cursor.dir/vnd.tassioauad.moviecheck.model.entity.user";
            case CODE_MOVIE_INTEREST:
                return "vnd.android.cursor.dir/vnd.tassioauad.moviecheck.model.entity.movieinterest";
            case CODE_MOVIE_NOT_INTEREST:
                return "vnd.android.cursor.dir/vnd.tassioauad.moviecheck.model.entity.movienotinterest";
            case CODE_MOVIE_WATCHED:
                return "vnd.android.cursor.dir/vnd.tassioauad.moviecheck.model.entity.moviewatched";
        }
        throw new IllegalArgumentException("Unsupported URI for insertion: " + uri);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long insertId;
        switch (uriMatcher.match(uri)) {
            case CODE_MOVIE:
                insertId = database.insert(MovieDaoImpl.TABLE_NAME, null, values);
                break;
            case CODE_USER:
                insertId = database.insert(UserDaoImpl.TABLE_NAME, null, values);
                break;
            case CODE_MOVIE_WATCHED:
                insertId = database.insert(MovieWatchedDaoImpl.TABLE_NAME, null, values);
                break;
            case CODE_MOVIE_INTEREST:
                insertId = database.insert(MovieInterestDaoImpl.TABLE_NAME, null, values);
                break;
            case CODE_MOVIE_NOT_INTEREST:
                insertId = database.insert(MovieNotInterestDaoImpl.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI for insertion: " + uri);
        }
        Uri itemUri = ContentUris.withAppendedId(uri, insertId);
        getContext().getContentResolver().notifyChange(itemUri, null);
        return itemUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleteCount;
        switch (uriMatcher.match(uri)) {
            case CODE_MOVIE:
                deleteCount = database.delete(MovieDaoImpl.TABLE_NAME, selection, selectionArgs);
                break;
            case CODE_USER:
                deleteCount = database.delete(UserDaoImpl.TABLE_NAME, selection, selectionArgs);
                break;
            case CODE_MOVIE_WATCHED:
                deleteCount = database.delete(MovieWatchedDaoImpl.TABLE_NAME, selection, selectionArgs);
                break;
            case CODE_MOVIE_INTEREST:
                deleteCount = database.delete(MovieInterestDaoImpl.TABLE_NAME, selection, selectionArgs);
                break;
            case CODE_MOVIE_NOT_INTEREST:
                deleteCount = database.delete(MovieNotInterestDaoImpl.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI for insertion: " + uri);
        }
        if (deleteCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleteCount;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updateCount;
        switch (uriMatcher.match(uri)) {
            case CODE_MOVIE:
                updateCount = database.update(MovieDaoImpl.TABLE_NAME, values, selection, selectionArgs);
                break;
            case CODE_USER:
                updateCount = database.update(UserDaoImpl.TABLE_NAME, values, selection, selectionArgs);
                break;
            case CODE_MOVIE_WATCHED:
                updateCount = database.update(MovieWatchedDaoImpl.TABLE_NAME, values, selection, selectionArgs);
                break;
            case CODE_MOVIE_INTEREST:
                updateCount = database.update(MovieInterestDaoImpl.TABLE_NAME, values, selection, selectionArgs);
                break;
            case CODE_MOVIE_NOT_INTEREST:
                updateCount = database.update(MovieNotInterestDaoImpl.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI for insertion: " + uri);
        }
        if (updateCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updateCount;
    }
}
