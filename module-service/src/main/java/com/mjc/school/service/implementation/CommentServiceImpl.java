package com.mjc.school.service.implementation;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.model.implementation.CommentEntity;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.comment.CommentDTOReq;
import com.mjc.school.service.dto.comment.CommentDTOResp;
import com.mjc.school.service.mapper.CommentDTOMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Scope("singleton")
public class CommentServiceImpl
        extends AbstractServiceImpl<CommentDTOReq, CommentDTOResp, CommentEntity, CommentRepository>
        implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    CommentDTOMapper mapper = Mappers.getMapper(CommentDTOMapper.class);
    @Override
    protected CommentEntity dtoToEntity(CommentDTOReq dto) {
        return mapper.dtoToCommentEntity(dto);
    }

    @Override
    protected List<CommentDTOResp> entitiesToDto(List<CommentEntity> commentEntities) {
        return mapper.commentEntitiesToDto(commentEntities);
    }

    @Override
    protected CommentDTOResp entityToDto(CommentEntity entity) {
        return mapper.commentEntityToDto(entity);
    }

    @Override
    protected Page<CommentDTOResp> pageToDtoPage(Page<CommentEntity> page) {
        return mapper.pageToDtoPage(page);
    }

    @Override
    protected CommentRepository getRepo() {
        return commentRepository;
    }

    @Override
    protected void updateEntityFromDto(CommentDTOReq commentDTOReq, CommentEntity entity) {
        mapper.updateEntityFromDto(commentDTOReq, entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTOResp> readCommentsByNewsId(Long newsId) {
        return entitiesToDto(commentRepository.readCommentsByNewsId(newsId));
    }
}
