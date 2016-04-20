package com.tassioauad.moviecheck.model.api;

public interface MovieApi extends AsyncService {
    void listUpcomingMovies();

    void listUpcomingMovies(int page);

    void listPopularMovies();

    void listPopularMovies(int page);
}
