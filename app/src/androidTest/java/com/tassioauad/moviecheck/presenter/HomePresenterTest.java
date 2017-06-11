package com.tassioauad.moviecheck.presenter;

import android.test.AndroidTestCase;

import com.tassioauad.moviecheck.entity.MovieBuilder;
import com.tassioauad.moviecheck.entity.UserBuilder;
import com.tassioauad.moviecheck.model.api.MovieApi;
import com.tassioauad.moviecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.moviecheck.model.api.asynctask.exception.BadRequestException;
import com.tassioauad.moviecheck.model.dao.UserDao;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.User;
import com.tassioauad.moviecheck.view.HomeView;

import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HomePresenterTest extends AndroidTestCase {

    HomeView view;
    HomePresenter presenter;
    MovieApi movieApi;
    UserDao userDao;
    ArgumentCaptor<ApiResultListener> apiResultListenerArgumentCaptor;

    public void setUp() throws Exception {
        super.setUp();
        movieApi = mock(MovieApi.class);
        view = mock(HomeView.class);
        userDao = mock(UserDao.class);
        apiResultListenerArgumentCaptor = ArgumentCaptor.forClass(ApiResultListener.class);
        presenter = new HomePresenter(view, movieApi, userDao);
    }

    public void testInit_LoggedUser() {
        User user = UserBuilder.aUser().build();
        when(userDao.getLoggedUser()).thenReturn(user);

        presenter.init();

        verify(view).showLoggedUser(user);
    }

    public void testInit_NotLoggedUser() {
        when(userDao.getLoggedUser()).thenReturn(null);

        presenter.init();

        verify(view, never()).showLoggedUser(any(User.class));
    }

    public void testInit_UserHasReadTutorial() {
        when(userDao.hasReadTutorial()).thenReturn(true);

        presenter.init();

        verify(view, never()).showTutorial();
    }

    public void testInit_UserHasNotReadTutorial() {
        when(userDao.hasReadTutorial()).thenReturn(false);

        presenter.init();

        verify(view).showTutorial();
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

        verify(view).showLoadingUpcomingMovies();
        verify(view).hideLoadingUpcomingMovies();
        verify(view).showUpcomingMovies(movieArrayList);
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

        verify(view).showLoadingUpcomingMovies();
        verify(view).hideLoadingUpcomingMovies();
        verify(view).warnAnyUpcomingMovieFounded();
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

        verify(view).showLoadingUpcomingMovies();
        verify(view).hideLoadingUpcomingMovies();
        verify(view).warnFailedToLoadUpcomingMovies();
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

        verify(view).showLoadingPopularMovies();
        verify(view).hideLoadingPopularMovies();
        verify(view).showPopularMovies(movieArrayList);
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

        verify(view).showLoadingPopularMovies();
        verify(view).hideLoadingPopularMovies();
        verify(view).warnAnyPopularMovieFounded();
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

        verify(view).showLoadingPopularMovies();
        verify(view).hideLoadingPopularMovies();
        verify(view).warnFailedToLoadPopularMovies();
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

        verify(view).showLoadingTopRatedMovies();
        verify(view).hideLoadingTopRatedMovies();
        verify(view).showTopRatedMovies(movieArrayList);
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

        verify(view).showLoadingTopRatedMovies();
        verify(view).hideLoadingTopRatedMovies();
        verify(view).warnAnyTopRatedMovieFounded();
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

        verify(view).showLoadingTopRatedMovies();
        verify(view).hideLoadingTopRatedMovies();
        verify(view).warnFailedToLoadTopRatedMovies();
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

        verify(view).showLoadingNowPlayingMovies();
        verify(view).hideLoadingNowPlayingMovies();
        verify(view).showNowPlayingMovies(movieArrayList);
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

        verify(view).showLoadingNowPlayingMovies();
        verify(view).hideLoadingNowPlayingMovies();
        verify(view).warnAnyNowPlayingMovieFounded();
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

        verify(view).showLoadingNowPlayingMovies();
        verify(view).hideLoadingNowPlayingMovies();
        verify(view).warnFailedToLoadNowPlayingMovies();
        verify(view, never()).showNowPlayingMovies(anyListOf(Movie.class));
        verify(view, never()).warnAnyNowPlayingMovieFounded();
    }

    public void testLogin() {
        User user = UserBuilder.aUser().build();
        
        presenter.login(user);

        verify(userDao).login(user);
        verify(view).showLoggedUser(user);
    }

    public void stop() {
        presenter.stop();

        verify(movieApi).cancelAllServices();
    }

    public void logout() {
        presenter.logout();

        verify(userDao).logout();
        verify(view).warnUserDesconnected();
    }

    public void informUserHasReadTutorial() {
        presenter.informUserHasReadTutorial();

        verify(userDao).informHasReadTutorial();
    }


}