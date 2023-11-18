package com.mjc.school.repository.implementation;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.implementation.NewsEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Scope("singleton")
public class NewsRepositoryImpl extends AbstractBaseRepositoryImpl<NewsEntity>
        implements NewsRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    protected Class<NewsEntity> getEntityClass() {
        return NewsEntity.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected String getTableName() {
        return "news";
    }

    @Override
    public List<NewsEntity> readNewsByCriteria(NewsEntity req) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(NewsEntity.class);
        Root<NewsEntity> news = criteriaQuery.from(NewsEntity.class);

        var predicates = new ArrayList<Predicate>();

        var authorName = req.getAuthor().getName();
        var authorId = req.getAuthor().getId();

        if (authorName != null || authorId != null) {
            var newsAuthors = news.join("author", JoinType.LEFT);
            if (authorName != null) {
                predicates.add(criteriaBuilder.equal(newsAuthors.get("name"), authorName));
            } else {
                predicates.add(criteriaBuilder.equal(newsAuthors.get("id"), authorId));
            }
        }

        var newsTitle = req.getTitle();
        var newsContent = req.getContent();

        if (newsTitle != null)
            predicates.add(criteriaBuilder.like(news.get("title"), "%" + newsTitle + "%"));
        if (newsContent != null)
            predicates.add(criteriaBuilder.like(news.get("content"), "%" + newsContent + "%"));

        var tags = req.getTags();
        if (tags.size() > 0) {
            var newsTags = news.join("tags", JoinType.LEFT);
            for (var tag : tags) {
                if (tag.getName() != null)
                    predicates.add(criteriaBuilder.equal(newsTags.get("name"), tag.getName()));
                if (tag.getId() != null)
                    predicates.add(criteriaBuilder.equal(newsTags.get("id"), tag.getId()));
            }
        }

        criteriaQuery.select(news).where(predicates.toArray(Predicate[]::new)).distinct(true);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
