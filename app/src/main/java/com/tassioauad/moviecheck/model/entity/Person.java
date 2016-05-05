package com.tassioauad.moviecheck.model.entity;


import android.os.Parcelable;

import java.util.Date;

public interface Person extends Parcelable {

    Long getId();

    String getName();

    String getPlaceOfBith();

    Date getBirthday();

    String getBiography();

    Date getDeathday();

    String getProfilePath();
}
