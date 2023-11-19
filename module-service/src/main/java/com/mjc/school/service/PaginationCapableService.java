package com.mjc.school.service;

import com.mjc.school.service.dto.page.PageReq;
import org.springframework.data.domain.Page;

public interface PaginationCapableService<T, R, K> {
    Page<R> readAll(PageReq req);

    R readById(K id);

    R create(T createRequest);

    R update(T updateRequest);

    void deleteById(K id);
}
