package com.mjc.school.service.validator.implementation;

import com.mjc.school.service.AuthorService;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.news.NewsDTOReq;
import com.mjc.school.service.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewsValidator extends BaseValidator<NewsDTOReq> {
    @Autowired
    private NewsService newsService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private TagService tagService;
    private final int titleLengthFrom = 3;
    private final int titleLengthTo = 50;
    private final int contentLengthFrom = 3;
    private final int contentLengthTo = 355;

    @Override
    public void validate(NewsDTOReq req, String reqType) {
        if (reqType.equals("create"))
            validateCreation(req);
        else if (reqType.equals("update")) {
            validateUpdate(req);
        }
    }

    private void validateCreation(NewsDTOReq req) {
        validateNullId(req.getId());
        validateString(req.getTitle(), titleLengthFrom, titleLengthTo);
        validateString(req.getContent(), contentLengthFrom, contentLengthTo);
        validateId(req.getAuthorId(), authorService);
        for (var tagId : req.getTagIds())
            if (tagService.readById(tagId) == null)
                validateId(tagId, tagService);
    }

    private void validateUpdate(NewsDTOReq req) {
        validateId(req.getId(), newsService);
        boolean changesExist = false;
        if (req.getTitle() != null) {
            validateString(req.getTitle(), titleLengthFrom, titleLengthTo);
            changesExist = true;
        }
        if (req.getContent() != null) {
            validateString(req.getContent(), contentLengthFrom, contentLengthTo);
            changesExist = true;
        }
        if (req.getAuthorId() != null) {
            validateId(req.getAuthorId(), authorService);
            changesExist = true;
        }
        if (req.getTagIds() != null) {
            for (var tagId : req.getTagIds())
                if (tagService.readById(tagId) == null)
                    validateId(tagId, tagService);
            changesExist = true;
        }
        if (!changesExist)
            throw new BadRequestException("nothing to update");
    }
}
