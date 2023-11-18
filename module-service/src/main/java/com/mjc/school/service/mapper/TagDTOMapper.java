package com.mjc.school.service.mapper;


import com.mjc.school.repository.model.implementation.TagEntity;
import com.mjc.school.repository.page.Page;
import com.mjc.school.service.dto.page.PageDTOResp;
import com.mjc.school.service.dto.tag.TagDTOReq;
import com.mjc.school.service.dto.tag.TagDTOResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public abstract class TagDTOMapper {
    public abstract TagDTOResp entityToResp(TagEntity tagEntity);

    @Mapping(ignore = true, target = "news")
    public abstract void updateEntityFromDto(TagDTOReq req, @MappingTarget TagEntity entity);

    @Mapping(ignore = true, target = "news")
    public abstract TagEntity reqToEntity(TagDTOReq tagDTOReq);
    public abstract List<TagDTOResp> entitiesToResps(List<TagEntity> tagEntities);

    public List<TagEntity> tagIdToEntity(List<Long> ids) {
        List<TagEntity> tagEntities = new ArrayList<>();
        for (var id : ids) {
            var tagEntity = new TagEntity();
            tagEntity.setId(id);
            tagEntities.add(tagEntity);
        }
        return tagEntities;
    }

    public abstract PageDTOResp<TagDTOResp> authorsPageToDto(Page<TagEntity> page);
}