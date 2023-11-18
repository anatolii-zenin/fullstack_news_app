package com.mjc.school.service;

import com.mjc.school.service.dto.author.AuthorDTOReq;
import com.mjc.school.service.dto.page.PageDTOReq;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.implementation.AuthorServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;


public class AuthorServiceTests {
    private AuthorService authorService;
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
    public void CreateReadAuthorTest() {
        authorService = context.getBean(AuthorServiceImpl.class);

        var testEntries = 5;
        for (int i = 0; i < testEntries; i++) {
            String authorName = "testAuthor" + i;

            var authorId = testHelper.createAuthor(authorName);
            var author = authorService.readById(authorId);

            assertEquals("Entry id is not as expected", authorId, author.getId());
            assertEquals("Entry name is not as expected", authorName, author.getName());
        }

        var allEntries = authorService.readAll(new PageDTOReq(1, 10, "id", "asc"));
        assertEquals("Incorrect number of entries:", testEntries, allEntries.getData().size());
    }

    @Test
    public void UpdateAuthorTest() {
        authorService = context.getBean(AuthorServiceImpl.class);

        String authorName = "testAuthor";
        String authorNameUpdated = "testAuthorUpdated";

        var authorReq = new AuthorDTOReq();
        authorReq.setName(authorName);
        var id = authorService.create(authorReq).getId();
        var entry = authorService.readById(id);
        assertEquals("Created entry is not as expected", authorName, entry.getName());

        authorReq = new AuthorDTOReq();
        authorReq.setName(authorNameUpdated);
        authorReq.setId(id);
        authorService.update(authorReq);
        entry = authorService.readById(id);
        assertEquals("Updated entry is not as expected", authorNameUpdated, entry.getName());
    }

    @Test
    public void DeleteAuthorTest() {
        authorService = context.getBean(AuthorServiceImpl.class);

        String authorName = "testAuthor";

        var authorReq = new AuthorDTOReq();
        authorReq.setName(authorName);
        var id = authorService.create(authorReq).getId();
        var entry = authorService.readById(id);
        assertEquals("Created entry is not as expected", authorName, entry.getName());

        authorService.deleteById(id);
        assertThrows(NotFoundException.class, () -> authorService.readById(id));
    }

    private void prepareServices() {
        authorService = context.getBean(AuthorServiceImpl.class);
        testHelper.setAuthorService(authorService);
    }

}
