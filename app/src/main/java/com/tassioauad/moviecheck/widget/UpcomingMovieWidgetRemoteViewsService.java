package com.tassioauad.moviecheck.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.view.activity.MovieProfileActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class UpcomingMovieWidgetRemoteViewsService extends RemoteViewsService {

    private static final String KEY_MOVIELIST = "MOVIELIST";

    private List<Movie> movieList;
    private int widgetId;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        movieList = Arrays.asList(new Gson().fromJson(intent.getStringExtra(KEY_MOVIELIST), Movie[].class));
        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);


        return new RemoteViewsFactory() {

            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {

            }

            @Override
            public void onDestroy() {

            }

            @Override
            public int getCount() {
                return movieList.size();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                final RemoteViews remoteViews = getLoadingView();

                Movie movie = movieList.get(position);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getString(R.string.general_date), Locale.getDefault());
                final String posterUrl = getString(R.string.imagetmdb_baseurl) + movie.getPosterUrl();

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.with(getApplicationContext()).load(posterUrl).into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                remoteViews.setImageViewBitmap(R.id.imageview_poster, bitmap);
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                                remoteViews.setImageViewResource(R.id.imageview_poster, R.drawable.noimage);
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                                remoteViews.setImageViewResource(R.id.imageview_poster, R.drawable.noimage);
                            }
                        });
                    }
                });

                remoteViews.setTextViewText(R.id.textview_releasedate, simpleDateFormat.format(movie.getReleaseDate()));
                remoteViews.setTextViewText(R.id.textview_name, movie.getTitle());

                Intent fillInIntent = MovieProfileActivity.newIntent(getBaseContext(), movie);
                remoteViews.setOnClickFillInIntent(R.id.linearlayout_upcomingmovie, fillInIntent);

                return remoteViews;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_upcomingmovies_listviewitem);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                return movieList.get(position).getId();
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }

    public static Intent newIntent(Context context, int widgetId, List<Movie> movieList) {
        Intent intent = new Intent( context, UpcomingMovieWidgetRemoteViewsService.class );
        intent.putExtra(KEY_MOVIELIST, new Gson().toJson(movieList.toArray(new Movie[movieList.size()])));

        return  intent;
    }
}
