package com.tassioauad.moviecheck.dagger;

import com.tassioauad.moviecheck.model.api.ReviewApi;
import com.tassioauad.moviecheck.presenter.ListReviewPresenter;
import com.tassioauad.moviecheck.view.ListReviewView;
import com.tassioauad.moviecheck.view.fragment.ListReviewFragment;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {AppModule.class, ApiModule.class}, injects = ListReviewFragment.class)
public class ListReviewViewModule {

    private ListReviewView view;

    public ListReviewViewModule(ListReviewView view) {
        this.view = view;
    }

    @Provides
    public ListReviewPresenter provideListReviewPresenter(ReviewApi reviewApi) {
        return new ListReviewPresenter(view, reviewApi);
    }
}
