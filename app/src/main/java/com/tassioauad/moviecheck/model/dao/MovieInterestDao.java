package com.tassioauad.moviecheck.model.dao;

import com.tassioauad.moviecheck.model.entity.MovieInterest;

import java.util.List;

public interface MovieInterestDao {
    List<MovieInterest> listAll();

    void insert(MovieInterest movieInterest);
}
