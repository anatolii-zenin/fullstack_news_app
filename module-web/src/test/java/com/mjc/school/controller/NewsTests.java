package com.mjc.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.mjc.school.controller.TestHelper.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class NewsTests {

    @Test
    void createNewsTest() {
        var authorId = createAuthor(DEFAULT_AUTHOR_NAME);
        createNews(authorId,
                DEFAULT_NEWS_TITLE,
                DEFAULT_NEWS_CONTENT,
                new ArrayList<>());
    }

    @Test
    void readNewsByIdTest() {
        var authorId = createAuthor(DEFAULT_AUTHOR_NAME);
        var newsId = createNews(authorId,
                DEFAULT_NEWS_TITLE,
                DEFAULT_NEWS_CONTENT,
                new ArrayList<>());

        var reqBody = new JSONObject();
        reqBody.put("id", newsId);

        given().contentType("application/json").body(reqBody.toString())
                .when().get(NEWS + newsId)
                .then()
                .statusCode(200)
                .body("content", equalTo(DEFAULT_NEWS_CONTENT))
                .body("title", equalTo(DEFAULT_NEWS_TITLE))
                .body("id", equalTo(newsId));
    }

    @Test
    void readAllAuthorsTest() {
        var authorId = createAuthor(DEFAULT_AUTHOR_NAME);
        createNews(authorId,
                DEFAULT_NEWS_TITLE,
                DEFAULT_NEWS_CONTENT,
                new ArrayList<>());
        createNews(authorId,
                DEFAULT_NEWS_TITLE,
                DEFAULT_NEWS_CONTENT,
                new ArrayList<>());

        given().contentType("application/json")
                .when().get(NEWS)
                .then()
                .statusCode(200)
                .body("content", hasSize(greaterThanOrEqualTo(2)));
    }

    @Test
    void updateNewsTest() {
        String newsTitleUpdated = "updated";

        var authorId = createAuthor(DEFAULT_AUTHOR_NAME);
        var newsId = createNews(authorId,
                DEFAULT_NEWS_TITLE,
                DEFAULT_NEWS_CONTENT,
                new ArrayList<>());

        var reqBody = new JSONObject();
        reqBody.put("id", newsId);
        reqBody.put("title", newsTitleUpdated);

        given().contentType("application/json").body(reqBody.toString())
                .when().patch(NEWS + newsId)
                .then()
                .statusCode(200)
                .body("title", equalTo(newsTitleUpdated));
    }

    @Test
    void deleteNewsTest() {
        var authorId = createAuthor(DEFAULT_AUTHOR_NAME);
        var newsId = createNews(authorId,
                DEFAULT_NEWS_TITLE,
                DEFAULT_NEWS_CONTENT,
                new ArrayList<>());

        given()
                .when().delete(NEWS + newsId)
                .then()
                .statusCode(204);
    }
}
