package com.mjc.school.controller;

import org.springframework.data.domain.Page;

public interface BaseController<T, R, K> {

    Page<R> readAll(int page, int size, String sortBy, String order);

    R readById(K id);

    R create(T createRequest);

    R update(K id, T updateRequest);

    void deleteById(K id);
}
