package com.mjc.school.service.implementation;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.implementation.NewsEntity;
import com.mjc.school.repository.page.Page;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.news.NewsDTOReq;
import com.mjc.school.service.dto.news.NewsDTOResp;
import com.mjc.school.service.dto.page.PageDTOResp;
import com.mjc.school.service.mapper.NewsDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("singleton")
public class NewsServiceImpl
        extends BaseServiceImpl<NewsDTOReq, NewsDTOResp, NewsEntity, NewsRepository>
        implements NewsService {
    @Autowired
    NewsRepository newsRepository;
    @Autowired
    NewsDTOMapper mapper;

    @Override
    protected NewsEntity dtoToEntity(NewsDTOReq newsDTOReq) {
        return mapper.newsReqToEntity(newsDTOReq);
    }

    @Override
    protected List<NewsDTOResp> entitiesToDtos(List<NewsEntity> newsEntities) {
        return mapper.newsEntitiesToDto(newsEntities);
    }

    @Override
    protected NewsDTOResp entityToDto(NewsEntity newsEntity) {
        return mapper.newsToDto(newsEntity);
    }

    @Override
    protected PageDTOResp<NewsDTOResp> pageToDto(Page<NewsEntity> page) {
        return mapper.authorsPageToDto(page);
    }

    @Override
    protected NewsRepository getRepo() {
        return newsRepository;
    }

    @Override
    protected void updateEntityFromDto(NewsDTOReq req, NewsEntity entity) {
        mapper.updateEntityFromDto(req, entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsDTOResp> readByCriteria(NewsDTOReq req) {
        var news = newsRepository.readNewsByCriteria(dtoToEntity(req));
        var newsResps = new ArrayList<NewsDTOResp>();
        for (var entry : news)
            newsResps.add(entityToDto(entry));
        return newsResps;
    }
}
