package com.tassioauad.moviecheck.view.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tassioauad.moviecheck.R;

public class FullImageDialogFragment extends DialogFragment {

    private static final String BUNDLE_ARG_PHOTOURL = "bundle_arg_photourl";
    private float mx;
    private float my;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialogfragment_fullimage, null);
        final ImageView imageViewPhoto = (ImageView) view.findViewById(R.id.imageview_fullphoto);
        Picasso.with(getActivity()).load(getArguments().getString(BUNDLE_ARG_PHOTOURL))
                .placeholder(R.drawable.noimage).into(imageViewPhoto);
        alertDialogBuilder.setView(view);
        imageViewPhoto.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent event) {

                float curX, curY;

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mx = event.getX();
                        my = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        curX = event.getX();
                        curY = event.getY();
                        imageViewPhoto.scrollBy((int) (mx - curX), (int) (my - curY));
                        mx = curX;
                        my = curY;
                        break;
                    case MotionEvent.ACTION_UP:
                        curX = event.getX();
                        curY = event.getY();
                        imageViewPhoto.scrollBy((int) (mx - curX), (int) (my - curY));
                        break;
                }

                return true;
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return alertDialog;
    }

    public static FullImageDialogFragment newInstance(String photoUrl) {
        FullImageDialogFragment fullImageDialogFragment = new FullImageDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ARG_PHOTOURL, photoUrl);
        fullImageDialogFragment.setArguments(bundle);

        return fullImageDialogFragment;
    }
}
