package com.tassioauad.moviecheck.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.dagger.ApiModule;
import com.tassioauad.moviecheck.model.entity.Movie;

import java.util.List;

import retrofit.Response;

import static java.net.HttpURLConnection.HTTP_OK;

public class UpcomingMovieUpdateService extends IntentService {

    public UpcomingMovieUpdateService() {
        super("UpcomingMovieUpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());

        int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        for (final int widgetId : allWidgetIds) {

            final RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget_upcomingmovies);

            try {
                Response<List<Movie>> response = new ApiModule().provideMovieResource(this).listUpComing(getString(R.string.themoviedbapi_key), 1).execute();
                switch (response.code()) {
                    case HTTP_OK:
                        remoteViews.setViewVisibility(R.id.progressbar, View.GONE);
                        remoteViews.setViewVisibility(R.id.linearlayout_loadfailed, View.GONE);
                        remoteViews.setViewVisibility(R.id.listview_movies, View.VISIBLE);
                        remoteViews.setRemoteAdapter(R.id.listview_movies, UpcomingMovieWidgetRemoteViewsService.newIntent(getApplicationContext(), response.body()));
                        appWidgetManager.updateAppWidget(widgetId, remoteViews);
                        break;
                }
            } catch (Exception e) {
                remoteViews.setViewVisibility(R.id.progressbar, View.GONE);
                remoteViews.setViewVisibility(R.id.listview_movies, View.GONE);
                remoteViews.setViewVisibility(R.id.linearlayout_loadfailed, View.VISIBLE);
                appWidgetManager.updateAppWidget(widgetId, remoteViews);
            }


        }
    }
}
