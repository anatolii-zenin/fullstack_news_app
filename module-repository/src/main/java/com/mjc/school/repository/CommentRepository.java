package com.mjc.school.repository;

import com.mjc.school.repository.model.implementation.CommentEntity;

import java.util.List;

public interface CommentRepository extends PaginationCapableRepository<CommentEntity, Long> {
    List<CommentEntity> readByNewsId(Long id);
}
