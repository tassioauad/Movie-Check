package com.tassioauad.moviecheck.view;

import com.tassioauad.moviecheck.model.entity.Image;
import com.tassioauad.moviecheck.model.entity.Media;
import com.tassioauad.moviecheck.model.entity.Video;

import java.util.List;

public interface ListMediaView {
    void warnAnyMediaFounded();

    void showMedias(List<Media> mediaList);

    void showLoadingMedias();

    void showVideos(List<Video> videoList);

    void hideLoadingMedias();

    void warnFailedToLoadMedias();

    void showImages(List<Image> imageList);
}
