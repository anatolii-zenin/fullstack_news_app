package com.mjc.school.service;

import com.mjc.school.service.dto.author.AuthorDTOReq;
import com.mjc.school.service.dto.news.NewsDTOReq;
import com.mjc.school.service.dto.tag.TagDTOReq;

import java.util.List;

public class TestHelper {
    private AuthorService authorService;
    private NewsService newsService;
    private TagService tagService;

    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    public Long createAuthor(String name) {
        var authorReq = new AuthorDTOReq();
        authorReq.setName(name);
        return authorService.create(authorReq).getId();
    }

    public Long createNews(Long authorId,
                           String newsTitle,
                           String newsContent,
                           List<Long> tagIds) {
        var newsReq = new NewsDTOReq();
        newsReq.setAuthorId(authorId);
        newsReq.setContent(newsContent);
        newsReq.setTitle(newsTitle);
        newsReq.setTagIds(tagIds);
        return newsService.create(newsReq).getId();
    }

    public Long createNews(Long authorId,
                           String newsTitle,
                           String newsContent) {
        var newsReq = new NewsDTOReq();
        newsReq.setAuthorId(authorId);
        newsReq.setContent(newsContent);
        newsReq.setTitle(newsTitle);
        return newsService.create(newsReq).getId();
    }

    public Long createTag(String name) {
        var tagReq = new TagDTOReq();
        tagReq.setName(name);
        return tagService.create(tagReq).getId();
    }

    public Long updateNews(Long newsId,
                            Long authorId,
                            String newsTitle,
                            String newsContent,
                            List<Long> tagIds) {
        var newsReq = new NewsDTOReq();
        newsReq.setId(newsId);
        newsReq.setAuthorId(authorId);
        newsReq.setContent(newsContent);
        newsReq.setTitle(newsTitle);
        newsReq.setTagIds(tagIds);
        return newsService.update(newsReq).getId();
    }

    public Long updateTag(Long id, String name) {
        var tagReq = new TagDTOReq();
        tagReq.setName(name);
        tagReq.setId(id);
        return tagService.update(tagReq).getId();
    }
}
