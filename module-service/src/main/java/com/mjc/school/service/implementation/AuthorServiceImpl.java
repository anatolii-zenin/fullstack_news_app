package com.mjc.school.service.implementation;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.model.implementation.AuthorEntity;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.author.AuthorDTOReq;
import com.mjc.school.service.dto.author.AuthorDTOResp;
import com.mjc.school.service.mapper.AuthorDTOMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("singleton")
public class AuthorServiceImpl
        extends AbstractServiceImpl<AuthorDTOReq, AuthorDTOResp, AuthorEntity, AuthorRepository>
        implements AuthorService {
    @Autowired
    AuthorRepository authorRepository;
    AuthorDTOMapper mapper = Mappers.getMapper(AuthorDTOMapper.class);

    @Override
    protected AuthorEntity dtoToEntity(AuthorDTOReq authorDTOReq) {
        return mapper.authorReqToEntity(authorDTOReq);
    }

    @Override
    protected List<AuthorDTOResp> entitiesToDto(List<AuthorEntity> authorEntities) {
        return mapper.authorsToDtoResp(authorEntities);
    }

    @Override
    protected AuthorDTOResp entityToDto(AuthorEntity authorEntity) {
        return mapper.authorToDtoResp(authorEntity);
    }

    @Override
    protected Page<AuthorDTOResp> pageToDtoPage(Page<AuthorEntity> page) {
        return mapper.pageToDtoPage(page);
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
        var entity = authorRepository.readAuthorByNewsId(newsId);
        return entity.map(this::entityToDto).orElse(null);
    }

    @Override
    public AuthorDTOResp readByName(String name) {
        return entityToDto(authorRepository.readAuthorByName(name).orElse(null));
    }
}
