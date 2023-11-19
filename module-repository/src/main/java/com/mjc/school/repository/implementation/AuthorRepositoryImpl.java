package com.mjc.school.repository.implementation;

import com.mjc.school.repository.model.implementation.AuthorEntity;
import com.mjc.school.repository.model.implementation.NewsEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Component
public class AuthorRepositoryImpl {
    @PersistenceContext
    EntityManager entityManager;

    public Optional<AuthorEntity> readAuthorByNewsId(Long newsId) {
        var newsById = entityManager.find(NewsEntity.class, newsId);
        return Optional.of(newsById.getAuthor());
    }
}
