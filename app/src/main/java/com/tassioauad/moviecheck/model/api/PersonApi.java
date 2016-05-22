package com.tassioauad.moviecheck.model.api;

public interface PersonApi extends AsyncService {
    void findById(Long personId);

    void listByName(String name, int page);

    void listByName(String name);
}
