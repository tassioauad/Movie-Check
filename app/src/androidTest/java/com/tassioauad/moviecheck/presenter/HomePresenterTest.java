package com.tassioauad.moviecheck.presenter;

import android.test.AndroidTestCase;

import com.tassioauad.moviecheck.entity.MovieBuilder;
import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.moviecheck.model.api.asynctask.exception.BadRequestException;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.view.HomeView;

import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class HomePresenterTest extends AndroidTestCase {

    HomeView view;
    HomePresenter presenter;
    MovieApi movieApi;
    ArgumentCaptor<ApiResultListener> apiResultListenerArgumentCaptor;

    public void setUp() throws Exception {
        super.setUp();
        movieApi = mock(MovieApi.class);
        view = mock(HomeView.class);
        apiResultListenerArgumentCaptor = ArgumentCaptor.forClass(ApiResultListener.class);
        presenter = new HomePresenter(view, movieApi);
    }

    public void testInit() {
        presenter.init();
    }

    public void testListUpComingMovies_Success() throws Exception {
        final ArrayList<Movie> movieArrayList = new ArrayList<>();
        movieArrayList.add(MovieBuilder.aMovie().build());
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onResult(movieArrayList);
                        return null;
                    }
                }).when(movieApi).listUpcomingMovies();
                return null;
            }
        }).when(movieApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.listUpComingMovies();

        verify(view, times(1)).showLoadingUpcomingMovies();
        verify(view, times(1)).hideLoadingUpcomingMovies();
        verify(view, times(1)).showUpComingMovies(movieArrayList);
        verify(view, never()).warnAnyUpcomingMovieFounded();
        verify(view, never()).warnFailedOnLoadUpcomingMovies();
    }

    public void testListUpComingMovies_NotFound() throws Exception {
        final ArrayList<Movie> movieArrayList = new ArrayList<>();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onResult(movieArrayList);
                        return null;
                    }
                }).when(movieApi).listUpcomingMovies();
                return null;
            }
        }).when(movieApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.listUpComingMovies();

        verify(view, times(1)).showLoadingUpcomingMovies();
        verify(view, times(1)).hideLoadingUpcomingMovies();
        verify(view, times(1)).warnAnyUpcomingMovieFounded();
        verify(view, never()).warnFailedOnLoadUpcomingMovies();
        verify(view, never()).warnFailedOnLoadUpcomingMovies();
    }

    public void testListUpComingMovies_Failed() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onException(new BadRequestException());
                        return null;
                    }
                }).when(movieApi).listUpcomingMovies();
                return null;
            }
        }).when(movieApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.listUpComingMovies();

        verify(view, times(1)).showLoadingUpcomingMovies();
        verify(view, times(1)).hideLoadingUpcomingMovies();
        verify(view, times(1)).warnFailedOnLoadUpcomingMovies();
        verify(view, never()).showUpComingMovies(anyListOf(Movie.class));
        verify(view, never()).warnAnyUpcomingMovieFounded();
    }
}