package com.mjc.school.service.validator.implementation;

import com.mjc.school.service.AuthorService;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.author.AuthorDTOResp;
import com.mjc.school.service.dto.comment.CommentDTOResp;
import com.mjc.school.service.dto.news.NewsDTOResp;
import com.mjc.school.service.dto.page.PageDTOReq;
import com.mjc.school.service.dto.tag.TagDTOResp;
import com.mjc.school.service.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class PageValidator {
    public void validate(Class<?> serviceClass, PageDTOReq req) {
        if (req.getOrder() == null || req.getSortedBy() == null)
            throw new BadRequestException("Null sorting parameters found. This should never happen");

        if(req.getPageNum() < 1)
            throw new BadRequestException("Page number has to be positive. Provided: " + req.getPageNum());
        if(req.getPageSize() < 1)
            throw new BadRequestException("Page size has to be positive. Provided: " + req.getPageSize());

        var interfaces = Arrays.asList(serviceClass.getInterfaces());
        if(interfaces.contains(AuthorService.class))
            validateSorting(AuthorDTOResp.class, req);
        else if (interfaces.contains(NewsService.class))
            validateSorting(NewsDTOResp.class, req);
        else if (interfaces.contains(CommentService.class))
            validateSorting(CommentDTOResp.class, req);
        else if (interfaces.contains(TagService.class))
            validateSorting(TagDTOResp.class, req);
    }

    private void validateSorting(Class<?> dtoClass, PageDTOReq req) {
        var dtoFields = dtoClass.getDeclaredFields();
        var fieldStrings = new ArrayList<String>();
        for (var field : dtoFields)
            fieldStrings.add(field.getName());
        if (!fieldStrings.contains(req.getSortedBy()))
            throw new BadRequestException("Unknown field to sort by: " + req.getSortedBy());
        if (!req.getOrder().equals("asc") && !req.getOrder().equals("desc"))
            throw new BadRequestException("Unknown sorting order: " + req.getOrder());
    }
}
