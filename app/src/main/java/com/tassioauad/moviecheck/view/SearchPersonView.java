package com.tassioauad.moviecheck.view;

import com.tassioauad.moviecheck.model.entity.Person;

import java.util.List;

public interface SearchPersonView {
    void showLoadingPerson();

    void warnAnyPersonFounded();

    void showPerson(List<Person> personList);

    void hideLoadingPerson();

    void warnFailedToLoadPerson();
}
