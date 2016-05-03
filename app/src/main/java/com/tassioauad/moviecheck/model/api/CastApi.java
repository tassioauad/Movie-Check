package com.tassioauad.moviecheck.model.api;

import com.tassioauad.moviecheck.model.entity.Movie;

public interface CastApi extends AsyncService {
    void listAllByMovie(Movie movie);
}
