package com.mjc.school.service;

import com.mjc.school.service.dto.page.PageDTOReq;
import com.mjc.school.service.dto.page.PageDTOResp;

public interface PaginationCapableService<T, R, K> {
    PageDTOResp<R> readAll(PageDTOReq req);

    R readById(K id);

    R create(T createRequest);

    R update(T updateRequest);

    boolean deleteById(K id);
}
