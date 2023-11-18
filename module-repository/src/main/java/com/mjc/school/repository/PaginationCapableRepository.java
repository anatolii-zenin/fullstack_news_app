package com.mjc.school.repository;

import com.mjc.school.repository.model.BaseEntity;
import com.mjc.school.repository.page.Page;
import com.mjc.school.repository.page.PageParams;

public interface PaginationCapableRepository<T extends BaseEntity<K>, K> extends BaseRepository<T, K> {
    Page<T> readAll(PageParams pageParams);
}
