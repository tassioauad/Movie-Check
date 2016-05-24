package com.tassioauad.moviecheck.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.model.entity.Movie;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UpcomingMovieWidgetRemoteViewsService extends RemoteViewsService {

    private static final String KEY_MOVIELIST = "MOVIELIST";

    private List<Movie> movieList;
    private int widgetId;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        movieList = intent.getParcelableArrayListExtra(KEY_MOVIELIST);
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
                String posterUrl = getString(R.string.imagetmdb_baseurl) + movie.getPosterUrl();

                /*remoteViews.setViewVisibility(R.id.progressbar, View.VISIBLE);
                new Callback() {
                    @Override
                    public void onSuccess() {
                        remoteViews.setViewVisibility(R.id.progressbar, View.GONE);
                    }

                    @Override
                    public void onError() {
                        remoteViews.setViewVisibility(R.id.progressbar, View.GONE);
                    }
                }*/
                Picasso.with(getBaseContext()).load(posterUrl).placeholder(R.drawable.noimage)
                        .into(remoteViews, R.id.imageview_poster, new int[]{widgetId});


                remoteViews.setTextViewText(R.id.textview_date, simpleDateFormat.format(movie.getReleaseDate()));

                return null;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_upcomingmovies);
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

    public static Intent newIntent(Context context, List<Movie> movieList) {
        Intent intent = new Intent(context, UpcomingMovieWidgetRemoteViewsService.class);
        intent.putParcelableArrayListExtra(KEY_MOVIELIST, new ArrayList<>(movieList));

        return  intent;
    }
}
