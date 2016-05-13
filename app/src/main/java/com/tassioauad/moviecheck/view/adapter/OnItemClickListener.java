package com.tassioauad.moviecheck.view.adapter;

import android.view.View;

public interface OnItemClickListener<MODEL> {
    void onClick(MODEL model, View view);
}