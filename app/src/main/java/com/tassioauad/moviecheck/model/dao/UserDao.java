package com.tassioauad.moviecheck.model.dao;

import com.tassioauad.moviecheck.model.entity.User;

public interface UserDao {
    void save(User user);

    void update(User user);

    void insert(User user);

    void login(User user);

    User getLoggedUser();

    void logout();
}
