package com.mjc.school.service;

import com.mjc.school.service.dto.news.NewsDTOReq;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.implementation.AuthorServiceImpl;
import com.mjc.school.service.implementation.NewsServiceImpl;
import com.mjc.school.service.implementation.TagServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;


public class NewsServiceTests {
    private AuthorService authorService;
    private NewsService newsService;
    private TagService tagService;
    private final TestHelper testHelper = new TestHelper();

    private static AnnotationConfigApplicationContext context;
    @BeforeEach
    public void setUp() {
        context = new AnnotationConfigApplicationContext(ServiceSpringConfig.class);
        prepareServices();
    }
    @AfterEach
    public void tearDown() {
        context.close();
    }

    @Test
    public void CreateReadNewsTest() {
        var testEntries = 5;
        for (int i = 0; i < testEntries; i++) {
            String authorName = "testAuthor" + i;
            String tagName = "tag" + i;
            String newsTitle = "newsTitle" + i;
            String newsContent = "newsContent" + i;

            var authorId = testHelper.createAuthor(authorName);
            var tagId = testHelper.createTag(tagName);
            var tagIds = new ArrayList<Long>();
            tagIds.add(tagId);
            var newsId = testHelper.createNews(authorId, newsTitle, newsContent, tagIds);

            var newsEntry = newsService.readById(newsId);
            var tagEntries = tagService.readByNewsId(newsId);

            assertEquals("Entry id is not as expected", newsId, newsEntry.getId());
            assertEquals("Entry title is not as expected", newsTitle, newsEntry.getTitle());
            assertEquals("Entry content is not as expected", newsContent, newsEntry.getContent());
            assertEquals("Tag is not tied to the news entry", tagId, tagEntries.get(0).getId());
        }
    }

    @Test
    public void updateNewsTest() {
        String authorName = "testAuthor";
        String newsTitle = "testTitle";
        String newsTitleUpdated = "testTitleUpdated";
        String newsContent = "testContent";
        String newsContentUpdated = "testContentUpdated";

        var authorId = testHelper.createAuthor(authorName);
        var authorEntry = authorService.readById(authorId);
        assertEquals("Created entry is not as expected", authorName, authorEntry.getName());

        var newsId = testHelper.createNews(authorId, newsTitle, newsContent);
        var newsEntry = newsService.readById(newsId);

        assertEquals("Entry title is not as expected", newsTitle, newsEntry.getTitle());
        assertEquals("Entry content is not as expected", newsContent, newsEntry.getContent());

        var newsReq = new NewsDTOReq();
        newsReq.setTitle(newsTitleUpdated);
        newsReq.setContent(newsContentUpdated);
        newsReq.setId(newsId);
        newsReq.setAuthorId(authorId);
        newsService.update(newsReq);
        newsEntry = newsService.readById(newsId);

        assertEquals("Updated title is not as expected", newsTitleUpdated, newsEntry.getTitle());
        assertEquals("Updated content is not as expected", newsContentUpdated, newsEntry.getContent());
    }

    @Test
    public void deleteNewsTest() {
        String authorName = "testAuthor";
        String newsTitle = "testTitle";
        String newsContent = "testContent";

        var authorId = testHelper.createAuthor(authorName);
        var authorEntry = authorService.readById(authorId);
        assertEquals("Created entry is not as expected", authorName, authorEntry.getName());

        var newsId = testHelper.createNews(authorId, newsTitle, newsContent);
        var newsEntry = newsService.readById(newsId);
        assertEquals("Entry title is not as expected", newsTitle, newsEntry.getTitle());
        assertEquals("Entry content is not as expected", newsContent, newsEntry.getContent());

        newsService.deleteById(newsId);
        assertThrows(NotFoundException.class, () -> newsService.readById(newsId));

        authorEntry = authorService.readById(authorId);
        assertEquals("Author is not persisted after deleting news", authorName, authorEntry.getName());
    }

    @Test
    public void deleteNewsByDeletingAuthorTest() {
        String authorName = "testAuthor";
        String newsTitle = "testTitle";
        String newsContent = "testContent";

        var authorId = testHelper.createAuthor(authorName);
        var authorEntry = authorService.readById(authorId);
        assertEquals("Created entry is not as expected", authorName, authorEntry.getName());

        var newsId = testHelper.createNews(authorId, newsTitle, newsContent);
        var newsEntry = newsService.readById(newsId);
        assertEquals("Entry title is not as expected", newsTitle, newsEntry.getTitle());
        assertEquals("Entry content is not as expected", newsContent, newsEntry.getContent());

        authorService.deleteById(authorId);
        assertThrows(NotFoundException.class, () -> authorService.readById(authorId));
        assertThrows(NotFoundException.class, () -> newsService.readById(newsId));
    }

    @Test
    public void selectNewsByCriteriaTest() {
        String authorName1 = "testAuthor1";
        String authorName2 = "testAuthor1";
        var authorId1 = testHelper.createAuthor(authorName1);
        var authorId2 = testHelper.createAuthor(authorName2);

        var tagIds = new ArrayList<Long>();
        var tagNum = 3;
        var tagNameBase = "tag#";

        for (int i = 0; i < tagNum; i++) {
            tagIds.add(testHelper.createTag(tagNameBase + i));
        }

        var newsTitle = "taggedNews";
        var newsContent = "taggedNews";
        testHelper.createNews(authorId1, newsTitle, newsContent, tagIds);
        testHelper.createNews(authorId2, newsTitle + "2", newsContent + "2", tagIds);
        testHelper.createNews(authorId1, "DoNotRead", "DoNotRead", new ArrayList<>());

        var tagsById = new ArrayList<Long>();
        tagsById.add(tagIds.get(0));

        var criteriaTagId = new NewsDTOReq();
        criteriaTagId.setTagIds(tagsById);

        var readByTagId = new NewsDTOReq();
        readByTagId.setTagIds(tagsById);

        var newsByCriteria = newsService.readByCriteria(readByTagId);

        assertEquals("The amount of entries is not as expected", 2, newsByCriteria.size());

        var readByAuthorId = new NewsDTOReq();
        readByAuthorId.setAuthorId(authorId1);

        newsByCriteria = newsService.readByCriteria(readByAuthorId);
        assertEquals("The amount of entries is not as expected", 2, newsByCriteria.size());
    }

    private void prepareServices() {
        authorService = context.getBean(AuthorServiceImpl.class);
        newsService = context.getBean(NewsServiceImpl.class);
        tagService = context.getBean(TagServiceImpl.class);
        testHelper.setNewsService(newsService);
        testHelper.setTagService(tagService);
        testHelper.setAuthorService(authorService);
    }
}
