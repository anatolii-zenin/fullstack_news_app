package com.mjc.school.controller;

import com.mjc.school.service.dto.page.PageDTOResp;

public interface BaseController<T, R, K> {

    PageDTOResp<R> readAll(int page, int size, String sortBy, String order);

    R readById(K id);

    R create(T createRequest);

    R update(K id, T updateRequest);

    void deleteById(K id);
}
