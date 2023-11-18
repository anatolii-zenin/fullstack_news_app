package com.mjc.school.service;

import com.mjc.school.service.dto.page.PageDTOReq;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.implementation.AuthorServiceImpl;
import com.mjc.school.service.implementation.NewsServiceImpl;
import com.mjc.school.service.implementation.TagServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;


public class TagServiceTests {
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
    public void CreateReadTagsTest() {
        String authorName = "testAuthor";
        var authorId = testHelper.createAuthor(authorName);

        var testEntries = 5;
        for (int i = 0; i < testEntries; i++) {

            String tagName = "tag" + i;
            String newsTitle = "newsTitle" + i;
            String newsContent = "newsContent" + i;

            var tagId = testHelper.createTag(tagName);
            var tag = tagService.readById(tagId);
            assertEquals("Entry id is not as expected", tagId, tag.getId());

            List<Long> tagIds = new ArrayList<>();
            tagIds.add(tagId);
            var newsId = testHelper.createNews(authorId, newsTitle, newsContent, tagIds);

            var newsEntry = newsService.readById(newsId);
            var tagEntries = tagService.readByNewsId(newsId);

            assertEquals("Entry id is not as expected", newsId, newsEntry.getId());
            assertEquals("Entry title is not as expected", newsTitle, newsEntry.getTitle());
            assertEquals("Entry content is not as expected", newsContent, newsEntry.getContent());
            assertEquals("Tag is not tied to the news entry", newsId, tagEntries.get(0).getId());
        }

        var allEntries = tagService.readAll(new PageDTOReq(1, 10, "id", "asc"));
        assertEquals("Incorrect number of entries:", testEntries, allEntries.getData().size());
    }

    @Test
    public void multipleTagsTest() {
        String authorName = "testAuthor";
        var authorId = testHelper.createAuthor(authorName);

        var tagIds = new ArrayList<Long>();
        var tagNum = 3;
        var tagNameBase = "multiTag";

        for (int i = 0; i < tagNum; i++) {
            tagIds.add(testHelper.createTag(tagNameBase + i));
        }

        var newsTitle = "multiTagNews";
        var newsContent = "multiTagNews";
        var newsId = testHelper.createNews(authorId, newsTitle, newsContent, tagIds);

        var tagsFromNews = tagService.readByNewsId(newsId);

        for (int i = 0; i < tagNum; i++) {
            assertEquals("Tag is not as expected", tagIds.get(i), tagsFromNews.get(i).getId());
            assertEquals("Tag is not as expected", tagNameBase + i, tagsFromNews.get(i).getName());
        }

        var allNewsEntries = newsService.readAll(new PageDTOReq(1, 10, "id", "asc"));
        assertEquals("Incorrect number of entries:", 1, allNewsEntries.getData().size());
    }

    @Test
    public void updateTagsTest() {
        String authorName = "testAuthor";
        String tagName = "tagTitle";
        String tagNameUpdated = "tagTitleUpd";
        String newsTitle = "newsTitle";
        String newsContent = "testContent";

        var authorId = testHelper.createAuthor(authorName);
        var authorEntry = authorService.readById(authorId);
        assertEquals("Created entry is not as expected", authorName, authorEntry.getName());

        var tagId = testHelper.createTag(tagName);
        List<Long> tagIds = new ArrayList<>();
        tagIds.add(tagId);

        var newsId = testHelper.createNews(authorId, newsTitle, newsContent, tagIds);
        var newsEntry = newsService.readById(newsId);
        var tagsFromNews = tagService.readByNewsId(newsId);

        assertEquals("Entry title is not as expected", newsTitle, newsEntry.getTitle());
        assertEquals("Entry content is not as expected", newsContent, newsEntry.getContent());
        assertEquals("Entry tag is not as expected", tagName, tagsFromNews.get(0).getName());
        assertEquals("Entry tag is not as expected", tagId, tagsFromNews.get(0).getId());

        tagId = testHelper.updateTag(tagId, tagNameUpdated);

        tagsFromNews = tagService.readByNewsId(newsId);

        assertEquals("Updated tag id is not as expected", tagId, tagsFromNews.get(0).getId());
        assertEquals("Updated tag is not as expected", tagNameUpdated, tagsFromNews.get(0).getName());
    }

    @Test
    public void deleteTagsTest() {
        String authorName = "testAuthor";
        String newsTitle = "testTitle";
        String newsContent = "testContent";
        String tagName = "tag";

        var authorId = testHelper.createAuthor(authorName);
        var authorEntry = authorService.readById(authorId);
        assertEquals("Created entry is not as expected", authorName, authorEntry.getName());

        var tagId = testHelper.createTag(tagName);
        List<Long> tagIds = new ArrayList<>();
        tagIds.add(tagId);

        var newsId = testHelper.createNews(authorId, newsTitle, newsContent, tagIds);
        var newsEntry = newsService.readById(newsId);
        var tagsFromNews = tagService.readByNewsId(newsId);
        assertEquals("Entry title is not as expected", newsTitle, newsEntry.getTitle());
        assertEquals("Entry content is not as expected", newsContent, newsEntry.getContent());
        assertEquals("Entry tag is not as expected", tagName, tagsFromNews.get(0).getName());

        testHelper.updateNews(newsId, authorId, newsTitle, newsContent, new ArrayList<>());
        tagService.deleteById(tagId);

        assertThrows(NotFoundException.class, () -> tagService.readById(tagId));
        assertEquals("Entry tag is not as expected", new ArrayList<Long>(), tagService.readByNewsId(newsId));
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
