package com.mjc.school.service.implementation;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.implementation.NewsEntity;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.news.NewsDTOReq;
import com.mjc.school.service.dto.news.NewsDTOResp;
import com.mjc.school.service.mapper.NewsDTOMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("singleton")
public class NewsServiceImpl
        extends AbstractServiceImpl<NewsDTOReq, NewsDTOResp, NewsEntity, NewsRepository>
        implements NewsService {
    @Autowired
    NewsRepository newsRepository;
    NewsDTOMapper mapper = Mappers.getMapper(NewsDTOMapper.class);

    @Override
    protected NewsEntity dtoToEntity(NewsDTOReq newsDTOReq) {
        return mapper.newsReqToEntity(newsDTOReq);
    }

    @Override
    protected List<NewsDTOResp> entitiesToDto(List<NewsEntity> newsEntities) {
        return mapper.newsEntitiesToDto(newsEntities);
    }

    @Override
    protected NewsDTOResp entityToDto(NewsEntity newsEntity) {
        return mapper.newsToDto(newsEntity);
    }

    @Override
    protected Page<NewsDTOResp> pageToDtoPage(Page<NewsEntity> page) {
        return mapper.pageToDtoPage(page);
    }

    @Override
    protected NewsRepository getRepo() {
        return newsRepository;
    }

    @Override
    protected void updateEntityFromDto(NewsDTOReq req, NewsEntity entity) {
        mapper.updateEntityFromDto(req, entity);
    }
}
