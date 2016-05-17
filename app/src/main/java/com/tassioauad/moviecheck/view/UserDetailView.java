package com.tassioauad.moviecheck.view;

import com.tassioauad.moviecheck.model.entity.Genre;
import com.tassioauad.moviecheck.model.entity.MovieInterest;
import com.tassioauad.moviecheck.model.entity.Person;
import com.tassioauad.moviecheck.model.entity.User;

import java.util.Date;
import java.util.List;

public interface UserDetailView {

    void showFullname(String fulname);

    void showEmail(String email);

    void showPhoto(String posterUrl);

    void showUser(User user);

    void showUpcomingInterests(List<MovieInterest> movieInterests);

    void showLoadingGenres();

    void warnAnyGenreFounded();

    void showGenres(List<Genre> genreList);

    void hideLoadingGenres();

    void warnFailedOnLoadGenres();

    void warnAnyInterestFound();
}
