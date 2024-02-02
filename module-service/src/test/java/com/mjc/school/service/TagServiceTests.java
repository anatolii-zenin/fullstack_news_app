package com.mjc.school.service;

import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.implementation.TagEntity;
import com.mjc.school.service.dto.tag.TagDTOReq;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.implementation.TagServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTests {
    @Mock
    private TagRepository tagRepositoryMock;
    @InjectMocks
    private TagServiceImpl tagService;
    private final Long mockedId = 1L;
    private final String mockedName = "name";
    @Test
    public void CreateTagTest() {
        when(tagRepositoryMock.save(any(TagEntity.class)))
                .thenReturn(new TagEntity(mockedId, mockedName, null));
        var tagReq = new TagDTOReq();
        tagReq.setName(mockedName);
        var newTag = tagService.create(tagReq);
        assertEquals(mockedName, newTag.getName());
    }

    @Test
    public void ReadTagTest() {
        when(tagRepositoryMock.findById(anyLong()))
                .thenReturn(Optional.of(new TagEntity(mockedId, mockedName, null))
                );

        var tag = tagRepositoryMock.findById(mockedId).orElse(null);
        assertNotNull(tag);
        Assertions.assertEquals(mockedName, tag.getName());
    }

    @Test
    public void DeleteTagTest() {
        when(tagRepositoryMock.findById(mockedId))
                .thenReturn(Optional.of(new TagEntity(mockedId, mockedName, null)));
        tagService.deleteById(mockedId);

        when(tagRepositoryMock.findById(mockedId)).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class, () -> tagService.readById(1L));
        Assertions.assertEquals("entity with id " + mockedId + " does not exist.\n", exception.getMessage());
    }
}
