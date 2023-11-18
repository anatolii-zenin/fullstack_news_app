package com.mjc.school.service.validator.implementation;

import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.author.AuthorDTOReq;
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
}
