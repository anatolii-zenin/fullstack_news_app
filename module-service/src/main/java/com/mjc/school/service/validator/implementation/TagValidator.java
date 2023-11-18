package com.mjc.school.service.validator.implementation;

import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.tag.TagDTOReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagValidator extends BaseValidator<TagDTOReq> {
    @Autowired
    private TagService tagService;
    private final int nameLengthFrom = 3;
    private final int nameLengthTo = 15;

    @Override
    public void validate(TagDTOReq req, String reqType) {
        validateString(req.getName(), nameLengthFrom, nameLengthTo);
        if (reqType.equals("update")) {
            validateId(req.getId(), tagService);
        }
    }
}
