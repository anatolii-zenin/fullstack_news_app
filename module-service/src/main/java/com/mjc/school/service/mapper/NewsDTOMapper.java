package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.implementation.NewsEntity;
import com.mjc.school.repository.page.Page;
import com.mjc.school.service.dto.news.NewsDTOReq;
import com.mjc.school.service.dto.news.NewsDTOResp;
import com.mjc.school.service.dto.page.PageDTOResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TagDTOMapper.class, AuthorDTOMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public abstract class NewsDTOMapper {
    public abstract NewsDTOResp newsToDto(NewsEntity newsEntity);

    @Mapping(ignore = true, target = "createDate")
    @Mapping(ignore = true, target = "lastUpdateDate")
    @Mapping(ignore = true, target = "comments")
    @Mapping(target = "author", source = "req.authorId")
    @Mapping(target = "tags", source = "req.tagIds")
    public abstract void updateEntityFromDto(NewsDTOReq req, @MappingTarget NewsEntity entity);

    @Mapping(ignore = true, target = "createDate")
    @Mapping(ignore = true, target = "lastUpdateDate")
    @Mapping(ignore = true, target = "comments")
    @Mapping(target = "author", source = "req.authorId")
    @Mapping(target = "tags", source = "req.tagIds")
    public abstract NewsEntity newsReqToEntity(NewsDTOReq req);

    public abstract List<NewsDTOResp> newsEntitiesToDto(List<NewsEntity> newsEntities);

    public abstract PageDTOResp<NewsDTOResp> authorsPageToDto(Page<NewsEntity> page);
}