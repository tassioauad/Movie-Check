package com.tassioauad.moviecheck.view;

import com.tassioauad.moviecheck.model.entity.Image;
import com.tassioauad.moviecheck.model.entity.Media;
import com.tassioauad.moviecheck.model.entity.Video;

import java.util.List;

public interface ListPersonMediaView {
    void warnAnyMediaFounded();

    void showMedias(List<Media> mediaList);

    void showLoadingMedias();

    void hideLoadingMedias();

    void warnFailedToLoadMedias();

    void showImages(List<Image> imageList);
}
