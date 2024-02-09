package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.implementation.CommentEntity;
import com.mjc.school.service.dto.comment.CommentDTOReq;
import com.mjc.school.service.dto.comment.CommentDTOResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class CommentDTOMapper {
    public abstract CommentDTOResp commentEntityToDto(CommentEntity commentEntity);
    public abstract List<CommentDTOResp> commentEntitiesToDto(List<CommentEntity> commentEntities);
    @Mapping(ignore = true, target = "createDate")
    @Mapping(ignore = true, target = "lastUpdateDate")
    @Mapping(ignore = true, target = "news")
    public abstract void updateEntityFromDto(CommentDTOReq req, @MappingTarget CommentEntity entity);
    @Mapping(ignore = true, target = "createDate")
    @Mapping(ignore = true, target = "lastUpdateDate")
    @Mapping(target = "news.id", source = "req.newsId")
    public abstract CommentEntity dtoToCommentEntity(CommentDTOReq req);

    public Page<CommentDTOResp> pageToDtoPage(Page<CommentEntity> entityPage) {
        return entityPage.map(this::commentEntityToDto);
    }
}
