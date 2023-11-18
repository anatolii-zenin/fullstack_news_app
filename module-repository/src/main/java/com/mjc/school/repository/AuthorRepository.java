package com.mjc.school.repository;

import com.mjc.school.repository.model.implementation.AuthorEntity;

import java.util.Optional;

public interface AuthorRepository extends PaginationCapableRepository<AuthorEntity, Long> {
    Optional<AuthorEntity> readByNewsId(Long newsId);
}
