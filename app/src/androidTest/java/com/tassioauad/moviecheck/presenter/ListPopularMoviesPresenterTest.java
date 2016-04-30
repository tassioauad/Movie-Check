package com.tassioauad.moviecheck.presenter;

import android.test.AndroidTestCase;

import com.tassioauad.moviecheck.entity.MovieBuilder;
import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.view.ListPopularMoviesView;

import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class ListPopularMoviesPresenterTest extends AndroidTestCase {

    ListPopularMoviesPresenter presenter;
    MovieApi movieApi;
    ListPopularMoviesView view;
    ArgumentCaptor<ApiResultListener> apiResultListenerArgumentCaptor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        view = mock(ListPopularMoviesView.class);
        movieApi = mock(MovieApi.class);
        apiResultListenerArgumentCaptor = ArgumentCaptor.forClass(ApiResultListener.class);
        presenter = new ListPopularMoviesPresenter(view, movieApi);
    }

    public void testLoadMovies_AnyMovie() throws Exception {
        final int page = 1;

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onResult(new ArrayList<>());
                        return null;
                    }
                }).when(movieApi).listPopularMovies(page);
                return null;
            }
        }).when(movieApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.loadMovies(page);

        verify(view, times(1)).showLoadingMovies();
        verify(view, times(1)).hideLoadingMovies();
        verify(view, times(1)).warnAnyMovieFounded();
        verify(view, never()).showMovies(anyListOf(Movie.class));
        verify(view, never()).warnFailedToLoadMovies();
    }

    public void testLoadMovies_MovieLoaded() throws Exception {
        final int page = 1;

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onResult(Arrays.asList(MovieBuilder.aMovie().build()));
                        return null;
                    }
                }).when(movieApi).listPopularMovies(page);
                return null;
            }
        }).when(movieApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.loadMovies(page);

        verify(view, times(1)).showLoadingMovies();
        verify(view, times(1)).hideLoadingMovies();
        verify(view, never()).warnAnyMovieFounded();
        verify(view, times(1)).showMovies(anyListOf(Movie.class));
        verify(view, never()).warnFailedToLoadMovies();
    }

    public void testLoadMovies_Failed() throws Exception {
        final int page = 1;

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onException(new Exception());
                        return null;
                    }
                }).when(movieApi).listPopularMovies(page);
                return null;
            }
        }).when(movieApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.loadMovies(page);

        verify(view, times(1)).showLoadingMovies();
        verify(view, times(1)).hideLoadingMovies();
        verify(view, never()).warnAnyMovieFounded();
        verify(view, never()).showMovies(anyListOf(Movie.class));
        verify(view, times(1)).warnFailedToLoadMovies();
    }
}