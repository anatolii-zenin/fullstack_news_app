package com.mjc.school.controller.impl;

import com.mjc.school.controller.AuthorController;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.author.AuthorDTOReq;
import com.mjc.school.service.dto.author.AuthorDTOResp;
import com.mjc.school.service.dto.filter.FilterReqDTO;
import com.mjc.school.service.dto.page.PageReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CAuthorController implements AuthorController {
    @Autowired
    AuthorService service;

    @Override
    @PostMapping(value = "/authors")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public Page<AuthorDTOResp> readAll(
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
    @GetMapping(value = "/authors/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @PermitAll
    public AuthorDTOResp readById(@PathVariable Long id) {
        return service.readById(id);
    }

    @Override
    @PostMapping(value = "/authors/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public AuthorDTOResp create(@RequestBody AuthorDTOReq createRequest) {
        return service.create(createRequest);
    }

    @Override
    @PatchMapping(value = "/authors/{id:\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public AuthorDTOResp update(@PathVariable Long id, @RequestBody AuthorDTOReq updateRequest) {
        updateRequest.setId(id);
        return service.update(updateRequest);
    }

    @Override
    @DeleteMapping(value = "/authors/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @Override
    @GetMapping(value = "/news/{id:\\d+}/author")
    @ResponseStatus(HttpStatus.OK)
    @PermitAll
    public AuthorDTOResp readByNewsId(@PathVariable Long id) {
        return service.readAuthorByNewsId(id);
    }
}
