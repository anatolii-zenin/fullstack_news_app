package com.mjc.school.repository.implementation;

import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.implementation.NewsEntity;
import com.mjc.school.repository.model.implementation.TagEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Scope("singleton")
public class TagRepositoryImpl extends AbstractBaseRepositoryImpl<TagEntity> implements TagRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    protected Class<TagEntity> getEntityClass() {
        return TagEntity.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected String getTableName() {
        return "tag";
    }

    @Override
    public List<TagEntity> readByNewsId(Long newsId) {
        var newsById = entityManager.find(NewsEntity.class, newsId);
        return newsById.getTags();
    }
}
