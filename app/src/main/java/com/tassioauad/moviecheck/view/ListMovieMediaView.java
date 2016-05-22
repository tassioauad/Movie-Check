package com.tassioauad.moviecheck.view;

import com.tassioauad.moviecheck.model.entity.Image;
import com.tassioauad.moviecheck.model.entity.Media;
import com.tassioauad.moviecheck.model.entity.Video;

import java.util.List;

public interface ListMovieMediaView {
    void warnAnyMediaFounded();

    void showLoadingMedias();

    void showVideos(List<Video> videoList);

    void showMedias(List<Media> mediaList);

    void hideLoadingMedias();

    void warnFailedToLoadMedias();

    void showImages(List<Image> imageList);
}
