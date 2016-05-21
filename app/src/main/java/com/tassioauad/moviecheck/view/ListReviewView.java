package com.tassioauad.moviecheck.view;

import com.tassioauad.moviecheck.model.entity.Review;

import java.util.List;

public interface ListReviewView {
    void warnAnyReviewFounded();

    void showLoadingReview();

    void showReviews(List<Review> reviewList);

    void showReviews();

    void hideLoadingReviews();

    void warnFailedToLoadReviews();
}
