package com.tassioauad.moviecheck.presenter;

import com.tassioauad.moviecheck.model.dao.UserDao;
import com.tassioauad.moviecheck.view.UserProfileView;

public class UserPerfilPresenter {

    private UserProfileView view;
    private UserDao userDao;

    public UserPerfilPresenter(UserProfileView view, UserDao userDao) {
        this.view = view;
        this.userDao = userDao;
    }

    public void init() {
        view.showUserName(userDao.getLoggedUser().getName());
    }
}
