package com.mjc.school.controller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import static io.restassured.RestAssured.given;

public class TestHelper {
    public static final String BASE = "http://localhost:8080/api/";
    public static final String NEWS = BASE + "news/";
    public static final String COMMENTS = BASE + "comments/";
    public static final String TAGS = BASE + "tags/";
    public static final String AUTHORS = BASE + "authors/";
    public static final String DEFAULT_NEWS_TITLE = "testNewsTitle";
    public static final String DEFAULT_NEWS_CONTENT = "testNewsContent";
    public static final String DEFAULT_AUTHOR_NAME = "testAuthor";
    public static final String DEFAULT_TAG_NAME = "testTag";
    public static final String DEFAULT_COMMENT = "testComment";

    public static int createAuthor(String name) {
        var reqBody = new JSONObject();
        reqBody.put("name", name);
        return postWithJson(reqBody, AUTHORS + "create");
    }

    public static int createTag(String name) {
        var reqBody = new JSONObject();
        reqBody.put("name", name);
        return postWithJson(reqBody, TAGS + "create");
    }

    public static int createNews(int authorId,
                                 String title,
                                 String content,
                                 List<Long> tagIds) {
        var reqBody = new JSONObject();
        reqBody.put("authorId", authorId);
        reqBody.put("title", title);
        reqBody.put("content", content);
        var tagsJson = new JSONArray();
        for (var tagId : tagIds) {
            tagsJson.put(tagId);
        }
        reqBody.put("tagIds", tagsJson);

        return postWithJson(reqBody, NEWS + "create");
    }

    public static int createComment(int newsId,
                                    String content) {
        var reqBody = new JSONObject();
        reqBody.put("newsId", newsId);
        reqBody.put("content", content);
        return postWithJson(reqBody, COMMENTS + "create");
    }

    public static int postWithJson(JSONObject req, String uri) {
        return given().contentType("application/json").body(req.toString())
                .when().post(uri)
                .then()
                .statusCode(201)
                .extract().response().path("id");
    }
}
