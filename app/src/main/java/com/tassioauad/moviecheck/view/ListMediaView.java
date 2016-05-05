package com.tassioauad.moviecheck.view;

import com.tassioauad.moviecheck.model.entity.Media;
import com.tassioauad.moviecheck.model.entity.Video;

import java.util.List;

public interface ListMediaView {
    void warnAnyVideoFounded();

    void showMedias(List<Media> mediaList);

    void showLoadingVideos();

    void showVideos(List<Video> videoList);

    void hideLoadingVideos();

    void warnFailedToLoadVideos();
}
