package com.mjc.school.repository.implementation;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.model.implementation.CommentEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Scope("singleton")
public class CommentRepositoryImpl extends AbstractBaseRepositoryImpl<CommentEntity>
        implements CommentRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    protected Class<CommentEntity> getEntityClass() {
        return CommentEntity.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected String getTableName() {
        return "comment";
    }

    @Override
    public List<CommentEntity> readByNewsId(Long id) {
        var findByNewsId = entityManager.createQuery(
                "SELECT c FROM " + getTableName() + " c " +
                "WHERE c.news.id=:newsId", CommentEntity.class);
        findByNewsId.setParameter("newsId", id);
        return findByNewsId.getResultList();
    }
}
