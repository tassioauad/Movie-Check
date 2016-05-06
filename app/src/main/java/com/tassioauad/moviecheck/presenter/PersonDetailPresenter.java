package com.tassioauad.moviecheck.presenter;


import com.tassioauad.moviecheck.model.api.PersonApi;
import com.tassioauad.moviecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.moviecheck.model.entity.Person;
import com.tassioauad.moviecheck.view.PersonDetailView;

public class PersonDetailPresenter {

    private PersonDetailView view;
    private PersonApi personApi;

    public PersonDetailPresenter(PersonDetailView view, PersonApi personApi) {
        this.view = view;
        this.personApi = personApi;
    }

    public void loadPerson(Long personId) {
        view.showLoading();
        personApi.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                if (object == null) {
                    view.warnPersonNotFound();
                } else {
                    Person person = (Person) object;
                    view.showPerson(person);
                }
                view.hideLoading();
            }

            @Override
            public void onException(Exception exception) {
                view.warnFailedToLoadPerson();
                view.hideLoading();
            }
        });
        personApi.findById(personId);
    }

}
