package com.tassioauad.moviecheck.presenter;

import com.tassioauad.moviecheck.model.api.ImageApi;
import com.tassioauad.moviecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.moviecheck.model.entity.Image;
import com.tassioauad.moviecheck.model.entity.Person;
import com.tassioauad.moviecheck.view.ListMovieMediaView;
import com.tassioauad.moviecheck.view.ListPersonMediaView;

import java.util.List;

public class ListPersonMediaPresenter {

    private ListPersonMediaView view;
    private ImageApi imageApi;

    public ListPersonMediaPresenter(ListPersonMediaView view, ImageApi imageApi) {
        this.view = view;
        this.imageApi = imageApi;
    }

    public void loadImages(Person person) {
        view.showLoadingMedias();
        imageApi.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Image> imageList = (List<Image>) object;
                if (imageList == null || imageList.size() == 0) {
                    view.warnAnyMediaFounded();
                } else {
                    view.showImages(imageList);
                }
                view.hideLoadingMedias();
            }

            @Override
            public void onException(Exception exception) {
                view.warnFailedToLoadMedias();
                view.hideLoadingMedias();
            }
        });
        imageApi.listAllByPerson(person);
    }

    public void stop() {
        imageApi.cancelAllServices();
    }
}
