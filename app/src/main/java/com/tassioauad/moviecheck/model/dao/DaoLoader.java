package com.tassioauad.moviecheck.model.dao;


import android.support.v4.app.FragmentActivity;

public interface DaoLoader {

    void setDaoListener(DaoListener daoListener);

    void setActivity(FragmentActivity activity);

}
