package com.mjc.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.mjc.school.controller.TestHelper.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CommentTests {

    @Test
    void createCommentTest() {
        var authorId = createAuthor(DEFAULT_AUTHOR_NAME);
        var newsId = createNews(authorId,
                DEFAULT_NEWS_TITLE,
                DEFAULT_NEWS_CONTENT,
                new ArrayList<>());
        createComment(newsId, DEFAULT_COMMENT);
    }

    @Test
    void readCommentByIdTest() {
        var authorId = createAuthor(DEFAULT_AUTHOR_NAME);
        var newsId = createNews(authorId,
                DEFAULT_NEWS_TITLE,
                DEFAULT_NEWS_CONTENT,
                new ArrayList<>());
        var commentId = createComment(newsId, DEFAULT_COMMENT);

        var reqBody = new JSONObject();
        reqBody.put("id", commentId);

        given().contentType("application/json").body(reqBody.toString())
                .when().get(COMMENTS + commentId)
                .then()
                .statusCode(200)
                .body("content", equalTo(DEFAULT_COMMENT))
                .body("id", equalTo(commentId));
    }

    @Test
    void readCommentByNewsIdTest() {
        var authorId = createAuthor(DEFAULT_AUTHOR_NAME);
        var newsId = createNews(authorId,
                DEFAULT_NEWS_TITLE,
                DEFAULT_NEWS_CONTENT,
                new ArrayList<>());
        var commentId = createComment(newsId, DEFAULT_COMMENT);

        var reqBody = new JSONObject();
        reqBody.put("id", commentId);

        given().contentType("application/json").body(reqBody.toString())
                .when().get(NEWS + newsId + "/comments")
                .then()
                .statusCode(200)
                .body("[0].content", equalTo(DEFAULT_COMMENT))
                .body("[0].id", equalTo(commentId));
    }

    @Test
    void readAllCommentsTest() {
        var authorId = createAuthor(DEFAULT_AUTHOR_NAME);
        var newsId = createNews(authorId,
                DEFAULT_NEWS_TITLE,
                DEFAULT_NEWS_CONTENT,
                new ArrayList<>());
        createComment(newsId, DEFAULT_COMMENT);
        createComment(newsId, DEFAULT_COMMENT);

        given().contentType("application/json")
                .when().get(COMMENTS)
                .then()
                .statusCode(200)
                .body("content", hasSize(greaterThanOrEqualTo(2)));
    }

    @Test
    void updateCommentTest() {
        String contentUpdated = "updated";

        var authorId = createAuthor(DEFAULT_AUTHOR_NAME);
        var newsId = createNews(authorId,
                DEFAULT_NEWS_TITLE,
                DEFAULT_NEWS_CONTENT,
                new ArrayList<>());
        var commentId = createComment(newsId, DEFAULT_COMMENT);

        var reqBody = new JSONObject();
        reqBody.put("id", commentId);
        reqBody.put("content", contentUpdated);

        given().contentType("application/json").body(reqBody.toString())
                .when().patch(COMMENTS + commentId)
                .then()
                .statusCode(200)
                .body("content", equalTo(contentUpdated));
    }

    @Test
    void deleteNewsTest() {
        var authorId = createAuthor(DEFAULT_AUTHOR_NAME);
        var newsId = createNews(authorId,
                DEFAULT_NEWS_TITLE,
                DEFAULT_NEWS_CONTENT,
                new ArrayList<>());
        var commentId = createComment(newsId, DEFAULT_COMMENT);

        given()
                .when().delete(COMMENTS + commentId)
                .then()
                .statusCode(204);
    }
}
