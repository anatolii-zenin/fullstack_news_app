package com.mjc.school.service.implementation;

import com.mjc.school.repository.model.BaseEntity;
import com.mjc.school.service.PaginationCapableService;
import com.mjc.school.service.dto.Request;
import com.mjc.school.service.dto.page.PageReq;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.filtering.FilterService;
import com.mjc.school.service.validator.annotations.Validate;
import com.mjc.school.service.validator.annotations.ValidatePage;
import com.mjc.school.service.validator.annotations.ValidateUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractServiceImpl<Req extends Request<Long>, Resp, Entity extends BaseEntity<Long>,
        Repository extends JpaRepository<Entity, Long> & JpaSpecificationExecutor<Entity>>
        implements PaginationCapableService<Req, Resp, Long> {

    @Autowired
    FilterService<Entity> filterService;

    @Override
    @Transactional(readOnly = true)
    public Page<Resp> readAll(@ValidatePage PageReq req) {
        var request = req.getPageable();
        Specification<Entity> specs = filterService.getSearchSpecification(req.getFilters());
        return pageToDtoPage(getRepo().findAll(specs, request));
    }

    @Override
    @Transactional(readOnly = true)
    public Resp readById(Long id) {
        var item = getRepo().findById(id);
        if(item.isEmpty())
            throw new NotFoundException("entity with id " + id + " does not exist.\n");
        return entityToDto(item.get());
    }

    @Override
    @Transactional
    public Resp create(@Validate Req createRequest) {
        return entityToDto(
                getRepo().save(dtoToEntity(createRequest))
        );
    }

    @Override
    @Transactional
    public Resp update(@ValidateUpdate Req updateRequest) {
        var id = updateRequest.getId();
        var entityToUpdate = getRepo().findById(id);
        if (entityToUpdate.isEmpty())
            throw new RuntimeException("entry with ID " + id + " does not exist.");
        var entity = entityToUpdate.get();

        updateEntityFromDto(updateRequest, entity);
        return entityToDto(
                getRepo().save(entity)
        );
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        getRepo().deleteById(id);
    }

    protected abstract Entity dtoToEntity(Req dto);

    protected abstract List<Resp> entitiesToDto(List<Entity> entities);

    protected abstract Resp entityToDto(Entity entity);
    protected abstract Page<Resp> pageToDtoPage(Page<Entity> page);
    
    protected abstract Repository getRepo();

    protected abstract void updateEntityFromDto(Req req, Entity entity);
}
