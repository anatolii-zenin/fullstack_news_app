package com.mjc.school.service.implementation;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.model.implementation.AuthorEntity;
import com.mjc.school.repository.page.Page;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.author.AuthorDTOReq;
import com.mjc.school.service.dto.author.AuthorDTOResp;
import com.mjc.school.service.dto.page.PageDTOResp;
import com.mjc.school.service.mapper.AuthorDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("singleton")
public class AuthorServiceImpl
        extends BaseServiceImpl<AuthorDTOReq, AuthorDTOResp, AuthorEntity, AuthorRepository>
        implements AuthorService {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    AuthorDTOMapper mapper;

    @Override
    protected AuthorEntity dtoToEntity(AuthorDTOReq authorDTOReq) {
        return mapper.authorReqToEntity(authorDTOReq);
    }

    @Override
    protected List<AuthorDTOResp> entitiesToDtos(List<AuthorEntity> authorEntities) {
        return mapper.authorsToDtoResp(authorEntities);
    }

    @Override
    protected AuthorDTOResp entityToDto(AuthorEntity authorEntity) {
        return mapper.authorToDtoResp(authorEntity);
    }

    @Override
    protected PageDTOResp<AuthorDTOResp> pageToDto(Page<AuthorEntity> page) {
        return mapper.authorsPageToDto(page);
    }

    @Override
    protected AuthorRepository getRepo() {
        return authorRepository;
    }

    @Override
    protected void updateEntityFromDto(AuthorDTOReq authorDTOReq, AuthorEntity entity) {
        mapper.updateEntityFromDto(authorDTOReq, entity);
    }

    @Override
    public AuthorDTOResp readAuthorByNewsId(Long newsId) {
        var entity = authorRepository.readByNewsId(newsId);
        return entity.map(this::entityToDto).orElse(null);
    }
}
