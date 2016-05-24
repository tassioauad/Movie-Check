package com.tassioauad.moviecheck.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RemoteViews;

import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.dagger.ApiModule;
import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.moviecheck.model.entity.Movie;

import java.util.List;

public class UpcomingMoviesWidget extends AppWidgetProvider {


    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


        ComponentName thisWidget = new ComponentName(context, UpcomingMoviesWidget.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        for (int widgetId : allWidgetIds) {

            final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_upcomingmovies);

            MovieApi movieApi = new ApiModule().provideMovieApi(context);

            movieApi.setApiResultListener(new ApiResultListener() {
                @Override
                public void onResult(Object object) {
                    remoteViews.setViewVisibility(R.id.progressbar, View.GONE);
                    remoteViews.setViewVisibility(R.id.linearlayout_loadfailed, View.GONE);
                    remoteViews.setViewVisibility(R.id.recyclerview_movies, View.VISIBLE);
                    remoteViews.setRemoteAdapter(R.id.recyclerview_movies, UpcomingMovieWidgetRemoteViewsService.newIntent(context, (List<Movie>) object));
                }

                @Override
                public void onException(Exception exception) {
                    remoteViews.setViewVisibility(R.id.progressbar, View.GONE);
                    remoteViews.setViewVisibility(R.id.recyclerview_movies, View.GONE);
                    remoteViews.setViewVisibility(R.id.linearlayout_loadfailed, View.VISIBLE);
                }
            });
            movieApi.listUpcomingMovies();

            appWidgetManager.updateAppWidget(widgetId, remoteViews);

        }

    }
}
