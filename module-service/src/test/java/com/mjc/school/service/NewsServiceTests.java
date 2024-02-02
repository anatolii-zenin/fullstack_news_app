package com.mjc.school.service;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.implementation.NewsEntity;
import com.mjc.school.service.dto.news.NewsDTOReq;
import com.mjc.school.service.dto.news.NewsDTOResp;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.implementation.NewsServiceImpl;
import com.mjc.school.service.mapper.NewsDTOMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NewsServiceTests {
    @Mock
    private NewsRepository newsRepoMock;
    @Mock
    private NewsDTOMapper newsDTOMapper;
    @InjectMocks
    private NewsServiceImpl newsService;
    private final Long mockedId = 1L;
    private final Long mockedAuthorId = 1L;
    private final String mockedTitle = "title";
    private final String mockedContent = "content";
    @Test
    public void CreateNewsTest() {
        when(newsRepoMock.save(any(NewsEntity.class)))
                .thenReturn(
                    new NewsEntity(
                        mockedId, mockedTitle, null,
                        null, null,
                        null, null, null)
                );
        when(newsDTOMapper.newsReqToEntity(any(NewsDTOReq.class)))
                .thenReturn(
                    new NewsEntity(
                        mockedId, mockedTitle, null,
                        null, null,
                        null, null, null)
                );
        when(newsDTOMapper.newsToDto(any(NewsEntity.class)))
                .thenReturn(
                    new NewsDTOResp(
                        mockedId, null, mockedTitle,
                        null, null,
                        null)
                );

        var newsReq = new NewsDTOReq();
        newsReq.setTitle(mockedTitle);
        newsReq.setAuthorId(mockedAuthorId);
        newsReq.setContent(mockedContent);
        var newNews = newsService.create(newsReq);
        assertNotNull(newNews);
        Assertions.assertEquals(mockedTitle, newNews.getTitle());
    }

    @Test
    public void ReadNewsTest() {
        when(newsRepoMock.findById(anyLong()))
                .thenReturn(Optional.of(new NewsEntity(
                        mockedId, mockedTitle, null,
                        null, null,
                        null, null, null))
                );

        var news = newsRepoMock.findById(mockedId).orElse(null);
        assertNotNull(news);
        Assertions.assertEquals(mockedTitle, news.getTitle());
    }

    @Test
    public void DeleteNewsTest() {
        when(newsRepoMock.findById(mockedId))
                .thenReturn(Optional.of(new NewsEntity(
                        mockedId, mockedTitle, null,
                        null, null,
                        null, null, null)));
        newsService.deleteById(mockedId);

        when(newsRepoMock.findById(mockedId)).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class, () -> newsService.readById(1L));
        Assertions.assertEquals("entity with id " + mockedId + " does not exist.\n", exception.getMessage());
    }
}
