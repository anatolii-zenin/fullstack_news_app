package com.mjc.school.repository.implementation;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.model.implementation.AuthorEntity;
import com.mjc.school.repository.model.implementation.NewsEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
@Scope("singleton")
public class AuthorRepositoryImpl extends AbstractBaseRepositoryImpl<AuthorEntity>
        implements AuthorRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    protected Class<AuthorEntity> getEntityClass() {
        return AuthorEntity.class;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected String getTableName() {
        return "author";
    }

    @Override
    public Optional<AuthorEntity> readByNewsId(Long newsId) {
        var newsById = entityManager.find(NewsEntity.class, newsId);
        return Optional.of(newsById.getAuthor());
    }
}
