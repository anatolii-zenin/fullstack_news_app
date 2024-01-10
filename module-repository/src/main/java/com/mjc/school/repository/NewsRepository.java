package com.mjc.school.repository;

import com.mjc.school.repository.model.implementation.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long>, JpaSpecificationExecutor<NewsEntity> {
}
