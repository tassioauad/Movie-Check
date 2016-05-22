package com.tassioauad.moviecheck.presenter;

import com.tassioauad.moviecheck.model.entity.Person;
import com.tassioauad.moviecheck.view.PersonProfileView;

public class PersonProfilePresenter {

    private PersonProfileView view;

    public PersonProfilePresenter(PersonProfileView view) {
        this.view = view;
    }

    public void init(Person person) {
        view.showPersonName(person.getName());
    }
}
