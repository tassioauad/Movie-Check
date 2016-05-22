package com.tassioauad.moviecheck.view.adapter;

import android.view.View;

import com.tassioauad.moviecheck.model.entity.MovieWatched;

public interface OnItemClickListener<MODEL> {
    void onClick(MODEL model, View view);

    void onLongClick(MODEL model, View view);
}