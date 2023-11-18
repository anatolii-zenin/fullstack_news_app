package com.mjc.school.repository.implementation;

import com.mjc.school.repository.PaginationCapableRepository;
import com.mjc.school.repository.model.BaseEntity;
import com.mjc.school.repository.page.Page;
import com.mjc.school.repository.page.PageParams;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;


@Repository
public abstract class AbstractBaseRepositoryImpl<T extends BaseEntity<Long>> implements PaginationCapableRepository<T, Long> {
    @Override
    public List<T> readAll() {
        var findAll = getEntityManager().createQuery(
            "SELECT a FROM " + getTableName() + " a", getEntityClass());
        return findAll.getResultList();
    }

    @Override
    public Page<T> readAll(PageParams pageParams) {
        var page = new Page<T>();
        page.setCurrentPage(pageParams.getPageNum());
        page.setSortedBy(pageParams.getSortedBy());

        var query = getEntityManager().createQuery(
            "SELECT a FROM " + getTableName() + " a " +
                    "ORDER BY a." + pageParams.getSortedBy() +
                    " " + pageParams.getOrder(),
                getEntityClass());

        var totalPages = (int) Math.ceil((double) query.getResultList().size() / pageParams.getPageSize());
        page.setTotalPages(totalPages);

        query.setMaxResults(pageParams.getPageSize());
        query.setFirstResult((pageParams.getPageNum() - 1) * pageParams.getPageSize());
        page.setData(query.getResultList());

        page.setLastPage(
                totalPages == pageParams.getPageNum()
        );

        return page;
    }

    @Override
    public Optional<T> readById(Long id) {
        return Optional.ofNullable(getEntityManager().find(getEntityClass(), id));
    }

    @Override
    public T create(T entity) {
        return merge(entity);
    }

    @Override
    public T update(T entity) {
        return merge(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        var obj = readById(id);
        if (obj.isPresent())
            getEntityManager().remove(obj.get());
        else
            return false;
        return readById(id).isEmpty();
    }

    @Override
    public boolean existById(Long id) {
        Optional<T> itemById = readById(id);
        return itemById.isPresent();
    }

    protected abstract Class<T> getEntityClass();
    protected abstract EntityManager getEntityManager();
    protected abstract String getTableName();

    private T merge(T entity) {
        var mergedEntity = getEntityManager().merge(entity);
        return getEntityManager().find(getEntityClass(), mergedEntity.getId());
    }
}
