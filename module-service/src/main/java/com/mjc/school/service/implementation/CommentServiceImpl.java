package com.mjc.school.service.implementation;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.model.implementation.CommentEntity;
import com.mjc.school.repository.page.Page;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.comment.CommentDTOReq;
import com.mjc.school.service.dto.comment.CommentDTOResp;
import com.mjc.school.service.dto.page.PageDTOResp;
import com.mjc.school.service.mapper.CommentDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Scope("singleton")
public class CommentServiceImpl
        extends BaseServiceImpl<CommentDTOReq, CommentDTOResp, CommentEntity, CommentRepository>
        implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentDTOMapper mapper;
    @Override
    protected CommentEntity dtoToEntity(CommentDTOReq dto) {
        return mapper.dtoToCommentEntity(dto);
    }

    @Override
    protected List<CommentDTOResp> entitiesToDtos(List<CommentEntity> commentEntities) {
        return mapper.commentEntitiesToDto(commentEntities);
    }

    @Override
    protected CommentDTOResp entityToDto(CommentEntity entity) {
        return mapper.commentEntityToDto(entity);
    }

    @Override
    protected PageDTOResp<CommentDTOResp> pageToDto(Page<CommentEntity> page) {
        return mapper.authorsPageToDto(page);
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
        return entitiesToDtos(commentRepository.readByNewsId(newsId));
    }
}
