package com.mjc.school.service.validator.implementation;

import com.mjc.school.service.CommentService;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.comment.CommentDTOReq;
import com.mjc.school.service.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentValidator extends BaseValidator<CommentDTOReq> {
    @Autowired
    CommentService commentService;
    @Autowired
    NewsService newsService;
    private final int contentLengthFrom = 3;
    private final int contentLengthTo = 355;
    @Override
    public void validate(CommentDTOReq req, String reqType) {
        if (reqType.equals("create"))
            validateCreate(req);
        else if (reqType.equals("update")) {
            validateUpdate(req);
        }
    }

    private void validateCreate(CommentDTOReq req) {
        validateNullId(req.getId());
        validateId(req.getNewsId(), newsService);
        validateString(req.getContent(), contentLengthFrom, contentLengthTo);
    }

    private void validateUpdate(CommentDTOReq req) {
        validateId(req.getId(), commentService);
        boolean changesExist = false;
        if (req.getContent() != null) {
            validateString(req.getContent(), contentLengthFrom, contentLengthTo);
            changesExist = true;
        }
        if (req.getNewsId() != null) {
            validateId(req.getNewsId(), newsService);
            changesExist = true;
        }
        if (!changesExist)
            throw new BadRequestException("nothing to update");
    }
}
