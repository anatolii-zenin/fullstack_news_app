package com.mjc.school.controller.impl;

import com.mjc.school.controller.TagController;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.filter.FilterReqDTO;
import com.mjc.school.service.dto.page.PageReq;
import com.mjc.school.service.dto.tag.TagDTOReq;
import com.mjc.school.service.dto.tag.TagDTOResp;
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
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CTagController implements TagController {
    @Autowired
    TagService service;

    @Override
    @GetMapping(value = "/tags")
    @ResponseStatus(HttpStatus.OK)
    @PermitAll
    public Page<TagDTOResp> readAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "order", required = false, defaultValue = "desc") String order,
            @RequestBody(required = false) List<FilterReqDTO> filters) {
        var pageReq = new PageReq(page, size, sortBy, order);
        pageReq.setFilters(Objects.requireNonNullElseGet(filters, ArrayList::new));
        return service.readAll(pageReq);
    }

    @Override
    @GetMapping(value = "/tags/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @PermitAll
    public TagDTOResp readById(@PathVariable Long id) {
        return service.readById(id);
    }

    @Override
    @PostMapping(value = "/tags/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasRole('USER')")
    public TagDTOResp create(@RequestBody TagDTOReq createRequest) {
        return service.create(createRequest);
    }

    @Override
    @PatchMapping(value = "/tags/{id:\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public TagDTOResp update(@PathVariable Long id, @RequestBody TagDTOReq updateRequest) {
        updateRequest.setId(id);
        return service.update(updateRequest);
    }

    @Override
    @DeleteMapping(value = "/tags/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @Override
    @GetMapping(value = "/news/{id:\\d+}/tags")
    @ResponseStatus(HttpStatus.OK)
    @PermitAll
    public List<TagDTOResp> readByNewsId(@PathVariable Long id) {
        return service.readByNewsId(id);
    }

    @Override
    @GetMapping(value = "/tags/by-name")
    @ResponseStatus(HttpStatus.OK)
    @PermitAll
    public TagDTOResp readByName(@RequestParam String name) {
        return service.readByName(name);
    }

}
