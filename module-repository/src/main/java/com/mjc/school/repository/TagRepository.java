package com.mjc.school.repository;

import com.mjc.school.repository.model.implementation.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {
    List<TagEntity> readTagsByNewsId(Long newsId);
}
