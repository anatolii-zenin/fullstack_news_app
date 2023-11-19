package com.mjc.school.repository.implementation;

import com.mjc.school.repository.model.implementation.CommentEntity;
import com.mjc.school.repository.model.implementation.NewsEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class CommentRepositoryImpl {
    @PersistenceContext
    EntityManager entityManager;

    public List<CommentEntity> readCommentsByNewsId(Long newsId) {
        var newsById = entityManager.find(NewsEntity.class, newsId);
        return newsById.getComments();
    }
}
