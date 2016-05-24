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
import com.tassioauad.moviecheck.model.api.asynctask.AsyncTaskResult;
import com.tassioauad.moviecheck.model.api.asynctask.ListUpComingMovieAsyncTask;
import com.tassioauad.moviecheck.model.entity.Movie;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UpcomingMoviesWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        Intent intent = new Intent(context.getApplicationContext(), UpcomingMovieUpdateService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

        context.startService(intent);
    }
}
