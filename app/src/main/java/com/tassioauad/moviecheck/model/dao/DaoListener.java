package com.tassioauad.moviecheck.model.dao;

public interface DaoListener<ENTITY> {

    void onLoad(ENTITY entity);

}
