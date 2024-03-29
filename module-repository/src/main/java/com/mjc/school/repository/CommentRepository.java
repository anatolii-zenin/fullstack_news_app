package com.mjc.school.repository;

import com.mjc.school.repository.model.implementation.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>, JpaSpecificationExecutor<CommentEntity> {
    List<CommentEntity> readCommentsByNewsId(Long id);
}
