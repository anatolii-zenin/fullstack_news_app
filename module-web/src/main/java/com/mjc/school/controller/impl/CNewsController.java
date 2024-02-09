package com.mjc.school.controller.impl;

import com.mjc.school.controller.NewsController;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.filter.FilterReqDTO;
import com.mjc.school.service.dto.news.NewsDTOReq;
import com.mjc.school.service.dto.news.NewsDTOResp;
import com.mjc.school.service.dto.page.PageReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping(value = "/api/news", produces = MediaType.APPLICATION_JSON_VALUE)
public class CNewsController implements NewsController {
    @Autowired
    NewsService service;

    @Override
    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    @PermitAll
    public Page<NewsDTOResp> readAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createDate") String sortBy,
            @RequestParam(name = "order", required = false, defaultValue = "desc") String order,
            @RequestBody(required = false) List<FilterReqDTO> filters) {
        var pageReq = new PageReq(page, size, sortBy, order);
        pageReq.setFilters(Objects.requireNonNullElseGet(filters, ArrayList::new));
        return service.readAll(pageReq);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @PermitAll
    public NewsDTOResp readById(@PathVariable Long id) {
        return service.readById(id);
    }

    @Override
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    public NewsDTOResp create(@RequestBody NewsDTOReq createRequest) {
        return service.create(createRequest);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public NewsDTOResp update(@PathVariable Long id, @RequestBody NewsDTOReq updateRequest) {
        updateRequest.setId(id);
        return service.update(updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

}
