package com.mjc.school.service.validator.implementation;

import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.author.AuthorDTOReq;
import com.mjc.school.service.dto.tag.TagDTOReq;
import com.mjc.school.service.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorValidator extends BaseValidator<AuthorDTOReq> {
    @Autowired
    private AuthorService authorService;
    private final int nameLengthFrom = 3;
    private final int nameLengthTo = 25;

    @Override
    public void validate(AuthorDTOReq req, String reqType) {
        validateString(req.getName(), nameLengthFrom, nameLengthTo);
        if (reqType.equals("update")) {
            validateId(req.getId(), authorService);
        }
    }

    private void validateTagDoesNotExist(TagDTOReq req) {
        if (authorService.readByName(req.getName()) != null)
            throw new BadRequestException("tag with provided name already exists: " + req.getName());
    }
}
