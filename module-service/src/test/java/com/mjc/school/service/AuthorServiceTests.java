package com.mjc.school.service;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.model.implementation.AuthorEntity;
import com.mjc.school.service.dto.author.AuthorDTOReq;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.implementation.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTests {
    @Mock
    private AuthorRepository authorRepositoryMock;
    @InjectMocks
    private AuthorServiceImpl authorService;
    private final Long mockedId = 1L;
    private final String mockedName = "name";
    @Test
    public void CreateAuthorTest() {
        when(authorRepositoryMock.save(any(AuthorEntity.class)))
                .thenReturn(new AuthorEntity(
                        mockedId, mockedName, null, null, null, null)
                );

        var authorReq = new AuthorDTOReq();
        authorReq.setName(mockedName);
        var newAuthor = authorService.create(authorReq);
        assertEquals(mockedName, newAuthor.getName());
    }

    @Test
    public void DeleteAuthorTest() {
        when(authorRepositoryMock.findById(mockedId))
                .thenReturn(Optional.of(new AuthorEntity(
                        mockedId, mockedName, null, null, null, null)
                ));
        authorService.deleteById(mockedId);

        when(authorRepositoryMock.findById(mockedId)).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class, () -> authorService.readById(1L));
        assertEquals("entity with id " + mockedId + " does not exist.\n", exception.getMessage());
    }

}
