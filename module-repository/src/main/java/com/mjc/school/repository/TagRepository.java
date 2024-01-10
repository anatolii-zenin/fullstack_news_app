package com.mjc.school.repository;

import com.mjc.school.repository.model.implementation.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long>, JpaSpecificationExecutor<TagEntity> {
    List<TagEntity> readTagsByNewsId(Long newsId);
    Optional<TagEntity> findByNameIgnoreCaseContaining(String name);
}
