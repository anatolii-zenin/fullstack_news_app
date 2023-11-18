package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.implementation.AuthorEntity;
import com.mjc.school.repository.page.Page;
import com.mjc.school.service.dto.author.AuthorDTOReq;
import com.mjc.school.service.dto.author.AuthorDTOResp;
import com.mjc.school.service.dto.page.PageDTOResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public abstract class AuthorDTOMapper {

    public abstract AuthorDTOResp authorToDtoResp(AuthorEntity authorEntity);
    public abstract List<AuthorDTOResp> authorsToDtoResp(List<AuthorEntity> authorEntities);

    @Mapping(ignore = true, target = "createDate")
    @Mapping(ignore = true, target = "lastUpdateDate")
    @Mapping(ignore = true, target = "news")
    public abstract void updateEntityFromDto(AuthorDTOReq req, @MappingTarget AuthorEntity entity);
    @Mapping(ignore = true, target = "createDate")
    @Mapping(ignore = true, target = "lastUpdateDate")
    @Mapping(ignore = true, target = "news")
    public abstract AuthorEntity authorReqToEntity(AuthorDTOReq authorDTOReq);

    public AuthorEntity authorIdToEntity(Long id) {
        var authorEntity = new AuthorEntity();
        authorEntity.setId(id);
        return authorEntity;
    }

    public abstract PageDTOResp<AuthorDTOResp> authorsPageToDto(Page<AuthorEntity> page);
}