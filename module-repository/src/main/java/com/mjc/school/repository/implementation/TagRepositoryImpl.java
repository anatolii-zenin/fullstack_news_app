package com.mjc.school.repository.implementation;

import com.mjc.school.repository.model.implementation.NewsEntity;
import com.mjc.school.repository.model.implementation.TagEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class TagRepositoryImpl {
    @PersistenceContext
    EntityManager entityManager;

    public List<TagEntity> readTagsByNewsId(Long newsId) {
        var newsById = entityManager.find(NewsEntity.class, newsId);
        return newsById.getTags();
    }
}
