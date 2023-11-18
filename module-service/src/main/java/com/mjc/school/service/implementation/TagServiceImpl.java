package com.mjc.school.service.implementation;

import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.implementation.TagEntity;
import com.mjc.school.repository.page.Page;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.page.PageDTOResp;
import com.mjc.school.service.dto.tag.TagDTOReq;
import com.mjc.school.service.dto.tag.TagDTOResp;
import com.mjc.school.service.mapper.TagDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Scope("singleton")
public class TagServiceImpl
        extends BaseServiceImpl<TagDTOReq, TagDTOResp, TagEntity, TagRepository>
        implements TagService {
    @Autowired
    TagRepository tagRepository;
    @Autowired
    TagDTOMapper mapper;
    @Override
    protected TagEntity dtoToEntity(TagDTOReq tagDTOReq) {
        return mapper.reqToEntity(tagDTOReq);
    }

    @Override
    protected List<TagDTOResp> entitiesToDtos(List<TagEntity> tagEntities) {
        return mapper.entitiesToResps(tagEntities);
    }

    @Override
    protected TagDTOResp entityToDto(TagEntity tagEntity) {
        return mapper.entityToResp(tagEntity);
    }

    @Override
    protected PageDTOResp<TagDTOResp> pageToDto(Page<TagEntity> page) {
        return mapper.authorsPageToDto(page);
    }

    @Override
    protected TagRepository getRepo() {
        return tagRepository;
    }

    @Override
    protected void updateEntityFromDto(TagDTOReq tagDTOReq, TagEntity entity) {
        mapper.updateEntityFromDto(tagDTOReq, entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDTOResp> readByNewsId(Long id) {
        return entitiesToDtos(tagRepository.readByNewsId(id));
    }
}
