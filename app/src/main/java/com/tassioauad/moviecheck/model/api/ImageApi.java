package com.tassioauad.moviecheck.model.api;

import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.Person;

public interface ImageApi extends AsyncService {
    void listAllByMovie(Movie movie);

    void listAllByPerson(Person person);
}
