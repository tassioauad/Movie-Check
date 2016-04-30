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

    public void testListUpcomingMovies_Success() throws Exception {
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

        presenter.listUpcomingMovies();

        verify(view, times(1)).showLoadingUpcomingMovies();
        verify(view, times(1)).hideLoadingUpcomingMovies();
        verify(view, times(1)).showUpcomingMovies(movieArrayList);
        verify(view, never()).warnAnyUpcomingMovieFounded();
        verify(view, never()).warnFailedToLoadUpcomingMovies();
    }

    public void testListUpcomingMovies_NotFound() throws Exception {
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

        presenter.listUpcomingMovies();

        verify(view, times(1)).showLoadingUpcomingMovies();
        verify(view, times(1)).hideLoadingUpcomingMovies();
        verify(view, times(1)).warnAnyUpcomingMovieFounded();
        verify(view, never()).warnFailedToLoadUpcomingMovies();
        verify(view, never()).warnFailedToLoadUpcomingMovies();
    }

    public void testListUpcomingMovies_Failed() throws Exception {
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

        presenter.listUpcomingMovies();

        verify(view, times(1)).showLoadingUpcomingMovies();
        verify(view, times(1)).hideLoadingUpcomingMovies();
        verify(view, times(1)).warnFailedToLoadUpcomingMovies();
        verify(view, never()).showUpcomingMovies(anyListOf(Movie.class));
        verify(view, never()).warnAnyUpcomingMovieFounded();
    }

    public void testListPopularMovies_Success() throws Exception {
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
                }).when(movieApi).listPopularMovies();
                return null;
            }
        }).when(movieApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.listPopularMovies();

        verify(view, times(1)).showLoadingPopularMovies();
        verify(view, times(1)).hideLoadingPopularMovies();
        verify(view, times(1)).showPopularMovies(movieArrayList);
        verify(view, never()).warnAnyPopularMovieFounded();
        verify(view, never()).warnFailedToLoadPopularMovies();
    }

    public void testListPopularMovies_NotFound() throws Exception {
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
                }).when(movieApi).listPopularMovies();
                return null;
            }
        }).when(movieApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.listPopularMovies();

        verify(view, times(1)).showLoadingPopularMovies();
        verify(view, times(1)).hideLoadingPopularMovies();
        verify(view, times(1)).warnAnyPopularMovieFounded();
        verify(view, never()).warnFailedToLoadPopularMovies();
        verify(view, never()).warnFailedToLoadPopularMovies();
    }

    public void testListPopularMovies_Failed() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onException(new BadRequestException());
                        return null;
                    }
                }).when(movieApi).listPopularMovies();
                return null;
            }
        }).when(movieApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.listPopularMovies();

        verify(view, times(1)).showLoadingPopularMovies();
        verify(view, times(1)).hideLoadingPopularMovies();
        verify(view, times(1)).warnFailedToLoadPopularMovies();
        verify(view, never()).showPopularMovies(anyListOf(Movie.class));
        verify(view, never()).warnAnyPopularMovieFounded();
    }

    public void testListTopRatedMovies_Success() throws Exception {
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
                }).when(movieApi).listTopRatedMovies();
                return null;
            }
        }).when(movieApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.listTopRatedMovies();

        verify(view, times(1)).showLoadingTopRatedMovies();
        verify(view, times(1)).hideLoadingTopRatedMovies();
        verify(view, times(1)).showTopRatedMovies(movieArrayList);
        verify(view, never()).warnAnyTopRatedMovieFounded();
        verify(view, never()).warnFailedToLoadTopRatedMovies();
    }

    public void testListTopRatedMovies_NotFound() throws Exception {
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
                }).when(movieApi).listTopRatedMovies();
                return null;
            }
        }).when(movieApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.listTopRatedMovies();

        verify(view, times(1)).showLoadingTopRatedMovies();
        verify(view, times(1)).hideLoadingTopRatedMovies();
        verify(view, times(1)).warnAnyTopRatedMovieFounded();
        verify(view, never()).warnFailedToLoadTopRatedMovies();
        verify(view, never()).warnFailedToLoadTopRatedMovies();
    }

    public void testListTopRatedMovies_Failed() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onException(new BadRequestException());
                        return null;
                    }
                }).when(movieApi).listTopRatedMovies();
                return null;
            }
        }).when(movieApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.listTopRatedMovies();

        verify(view, times(1)).showLoadingTopRatedMovies();
        verify(view, times(1)).hideLoadingTopRatedMovies();
        verify(view, times(1)).warnFailedToLoadTopRatedMovies();
        verify(view, never()).showTopRatedMovies(anyListOf(Movie.class));
        verify(view, never()).warnAnyTopRatedMovieFounded();
    }

    public void testListNowPlayingMovies_Success() throws Exception {
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
                }).when(movieApi).listNowPlayingMovies();
                return null;
            }
        }).when(movieApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.listNowPlayingMovies();

        verify(view, times(1)).showLoadingNowPlayingMovies();
        verify(view, times(1)).hideLoadingNowPlayingMovies();
        verify(view, times(1)).showNowPlayingMovies(movieArrayList);
        verify(view, never()).warnAnyNowPlayingMovieFounded();
        verify(view, never()).warnFailedToLoadNowPlayingMovies();
    }

    public void testListNowPlayingMovies_NotFound() throws Exception {
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
                }).when(movieApi).listNowPlayingMovies();
                return null;
            }
        }).when(movieApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.listNowPlayingMovies();

        verify(view, times(1)).showLoadingNowPlayingMovies();
        verify(view, times(1)).hideLoadingNowPlayingMovies();
        verify(view, times(1)).warnAnyNowPlayingMovieFounded();
        verify(view, never()).warnFailedToLoadNowPlayingMovies();
        verify(view, never()).warnFailedToLoadNowPlayingMovies();
    }

    public void testListNowPlayingMovies_Failed() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onException(new BadRequestException());
                        return null;
                    }
                }).when(movieApi).listNowPlayingMovies();
                return null;
            }
        }).when(movieApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.listNowPlayingMovies();

        verify(view, times(1)).showLoadingNowPlayingMovies();
        verify(view, times(1)).hideLoadingNowPlayingMovies();
        verify(view, times(1)).warnFailedToLoadNowPlayingMovies();
        verify(view, never()).showNowPlayingMovies(anyListOf(Movie.class));
        verify(view, never()).warnAnyNowPlayingMovieFounded();
    }
}