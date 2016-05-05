package com.tassioauad.moviecheck.presenter;

import com.tassioauad.moviecheck.model.api.ImageApi;
import com.tassioauad.moviecheck.model.api.VideoApi;
import com.tassioauad.moviecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.moviecheck.model.entity.Image;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.Video;
import com.tassioauad.moviecheck.view.ListMediaView;

import java.util.List;

public class ListMediaPresenter {

    private ListMediaView view;
    private VideoApi videoApi;
    private ImageApi imageApi;

    public ListMediaPresenter(ListMediaView view, VideoApi videoApi, ImageApi imageApi) {
        this.view = view;
        this.videoApi = videoApi;
        this.imageApi = imageApi;
    }

    public void loadVideos(Movie movie) {
        view.showLoadingMedias();
        videoApi.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Video> videoList = (List<Video>) object;
                if (videoList == null || videoList.size() == 0) {
                    view.warnAnyMediaFounded();
                } else {
                    view.showVideos(videoList);
                }
                view.hideLoadingMedias();
            }

            @Override
            public void onException(Exception exception) {
                view.warnFailedToLoadMedias();
                view.hideLoadingMedias();
            }
        });
        videoApi.listAllByMovie(movie);
    }

    public void loadImages(Movie movie) {
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
        imageApi.listAllByMovie(movie);
    }

    public void stop() {
        videoApi.cancelAllServices();
        imageApi.cancelAllServices();
    }
}
