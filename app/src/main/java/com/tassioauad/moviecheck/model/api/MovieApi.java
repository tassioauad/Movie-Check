package com.tassioauad.moviecheck.model.api;

public interface MovieApi extends AsyncService {
    void listUpcomingMovies();

    void listUpcomingMovies(int page);

    void listPopularMovies();

    void listPopularMovies(int page);

    void listTopRatedMovies();

    void listTopRatedMovies(int page);

    void listNowPlayingMovies();

    void listNowPlayingMovies(int page);
}
