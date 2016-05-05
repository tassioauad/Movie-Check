package com.tassioauad.moviecheck.model.api;

import com.tassioauad.moviecheck.model.entity.Movie;

public interface VideoApi extends AsyncService {
    void listAllByMovie(Movie movie);
}
