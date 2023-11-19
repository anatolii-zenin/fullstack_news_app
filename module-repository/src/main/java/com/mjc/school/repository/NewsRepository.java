package com.mjc.school.repository;

import com.mjc.school.repository.model.implementation.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
    List<NewsEntity> readNewsByCriteria(NewsEntity req);
}
