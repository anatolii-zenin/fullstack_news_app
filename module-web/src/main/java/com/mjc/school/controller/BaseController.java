package com.mjc.school.controller;

import com.mjc.school.service.dto.filter.FilterReqDTO;
import org.springframework.data.domain.Page;
import java.util.List;

public interface BaseController<T, R, K> {

    Page<R> readAll(int page, int size, String sortBy, String order, List<FilterReqDTO> filter);

    R readById(K id);

    R create(T createRequest);

    R update(K id, T updateRequest);

    void deleteById(K id);
}
