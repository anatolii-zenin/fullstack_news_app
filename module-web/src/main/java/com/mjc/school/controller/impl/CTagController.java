package com.mjc.school.controller.impl;

import com.mjc.school.controller.TagController;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.page.PageReq;
import com.mjc.school.service.dto.tag.TagDTOReq;
import com.mjc.school.service.dto.tag.TagDTOResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(produces = "application/json", tags = "Tags", description = "CRUD operations for tags")
public class CTagController implements TagController {
    @Autowired
    TagService service;

    @Override
    @GetMapping(value = "/tags")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "List all tags.",
            response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved requested data"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public Page<TagDTOResp> readAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "order", required = false, defaultValue = "asc") String order) {
        var pageReq = new PageReq(page, size, sortBy, order);
        return service.readAll(pageReq);
    }

    @Override
    @GetMapping(value = "/tags/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve tag by ID", response = TagDTOResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved requested data"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 404, message = "Tag with given ID was not found"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public TagDTOResp readById(@PathVariable Long id) {
        return service.readById(id);
    }

    @Override
    @PostMapping(value = "/tags/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create tag", response = TagDTOResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created news"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public TagDTOResp create(@RequestBody TagDTOReq createRequest) {
        return service.create(createRequest);
    }

    @Override
    @PatchMapping(value = "/tags/{id:\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update a tag with given ID", response = TagDTOResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the comment"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 404, message = "Tag with given ID was not found"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public TagDTOResp update(@PathVariable Long id, @RequestBody TagDTOReq updateRequest) {
        updateRequest.setId(id);
        return service.update(updateRequest);
    }

    @Override
    @DeleteMapping(value = "/tags/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a tag with given ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted the comment"),
            @ApiResponse(code = 404, message = "Tag with given ID was not found"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @Override
    @GetMapping(value = "/news/{id:\\d+}/tags")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Read tags by news ID", response = TagDTOResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved requested data"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 404, message = "News with given ID was not found"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public List<TagDTOResp> readByNewsId(@PathVariable Long id) {
        return service.readByNewsId(id);
    }
}
