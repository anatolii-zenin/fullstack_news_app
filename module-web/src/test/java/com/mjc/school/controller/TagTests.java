package com.mjc.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.mjc.school.controller.TestHelper.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TagTests {
    @Test
    void createTagTest() {
        createTag(DEFAULT_TAG_NAME);
    }

    @Test
    void readTagByIdTest() {
        var id = TestHelper.createTag(DEFAULT_TAG_NAME);

        var reqBody = new JSONObject();
        reqBody.put("id", id);

        given().contentType("application/json").body(reqBody.toString())
                .when().get(TAGS + id)
                .then()
                .statusCode(200)
                .body("name", equalTo(DEFAULT_TAG_NAME))
                .body("id", equalTo(id));
    }

    @Test
    void readTagByNewsIdTest() {
        var tagId = TestHelper.createTag(DEFAULT_TAG_NAME);
        var authorId = createAuthor(DEFAULT_AUTHOR_NAME);
        var tagIds = new ArrayList<Long>();
        tagIds.add((long) tagId);
        var newsId = createNews(authorId,
                DEFAULT_NEWS_TITLE,
                DEFAULT_NEWS_CONTENT,
                tagIds);

        var reqBody = new JSONObject();
        reqBody.put("id", newsId);

        given().contentType("application/json").body(reqBody.toString())
                .when().get(NEWS + newsId + "/tags")
                .then()
                .statusCode(200)
                .body("[0].name", equalTo(DEFAULT_TAG_NAME))
                .body("[0].id", equalTo(tagId));
    }

    @Test
    void readAllTagsTest() {
        createTag(DEFAULT_TAG_NAME);
        createTag(DEFAULT_TAG_NAME);

        given().contentType("application/json")
                .when().get(TAGS)
                .then()
                .statusCode(200)
                .body("content", hasSize(greaterThanOrEqualTo(2)));
    }

    @Test
    void updateTagTest() {
        String tagNameUpdated = DEFAULT_TAG_NAME + "Updated";

        var id = createTag(DEFAULT_TAG_NAME);

        var reqBody = new JSONObject();
        reqBody.put("name", tagNameUpdated);
        reqBody.put("id", id);

        given().contentType("application/json").body(reqBody.toString())
                .when().patch(TAGS + id)
                .then()
                .statusCode(200)
                .body("name", equalTo(tagNameUpdated));

        var reqBodyInvalid = new JSONObject();
        reqBodyInvalid.put("name", "a");
        reqBodyInvalid.put("id", id);

        given().contentType("application/json").body(reqBodyInvalid.toString())
                .when().patch(TAGS + id)
                .then()
                .statusCode(400);
    }

    @Test
    void deleteTagTest() {
        var id = createTag(DEFAULT_TAG_NAME);

        given()
                .when().delete(TAGS + id)
                .then()
                .statusCode(204);
    }
}
