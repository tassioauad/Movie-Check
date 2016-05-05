package com.tassioauad.moviecheck.model.api;

import com.tassioauad.moviecheck.model.entity.Movie;

public interface ReviewApi extends AsyncService {
    void listByMovies(Movie movie, int page);
}
