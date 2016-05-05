package com.tassioauad.moviecheck.presenter;

import com.tassioauad.moviecheck.model.api.VideoApi;
import com.tassioauad.moviecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.Video;
import com.tassioauad.moviecheck.view.ListMediaView;

import java.util.List;

public class ListMediaPresenter {

    private ListMediaView view;
    private VideoApi videoApi;

    public ListMediaPresenter(ListMediaView view, VideoApi videoApi) {
        this.view = view;
        this.videoApi = videoApi;
    }

    public void loadVideos(Movie movie) {
        view.showLoadingVideos();
        videoApi.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Video> videoList = (List<Video>) object;
                if (videoList == null || videoList.size() == 0) {
                    view.warnAnyVideoFounded();
                } else {
                    view.showVideos(videoList);
                }
                view.hideLoadingVideos();
            }

            @Override
            public void onException(Exception exception) {
                view.warnFailedToLoadVideos();
                view.hideLoadingVideos();
            }
        });
        videoApi.listAllByMovie(movie);
    }

    public void stop() {
        videoApi.cancelAllServices();
    }
}
