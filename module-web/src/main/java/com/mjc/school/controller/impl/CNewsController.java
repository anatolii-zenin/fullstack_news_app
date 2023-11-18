package com.mjc.school.controller.impl;

import com.mjc.school.controller.NewsController;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.news.NewsDTOReq;
import com.mjc.school.service.dto.news.NewsDTOResp;
import com.mjc.school.service.dto.page.PageDTOReq;
import com.mjc.school.service.dto.page.PageDTOResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/news", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(produces = "application/json", tags = "News", description = "CRUD operations for news")
public class CNewsController implements NewsController {
    @Autowired
    NewsService service;

    @Override
    @GetMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "List all news.",
            response = PageDTOResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved requested data"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public PageDTOResp<NewsDTOResp> readAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createDate") String sortBy,
            @RequestParam(name = "order", required = false, defaultValue = "desc") String order) {
        var pageReq = new PageDTOReq(page, size, sortBy, order);
        return service.readAll(pageReq);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve news by ID", response = NewsDTOResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved requested data"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 404, message = "News with given ID was not found"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public NewsDTOResp readById(@PathVariable Long id) {
        return service.readById(id);
    }

    @Override
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create news", response = NewsDTOResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created news"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public NewsDTOResp create(@RequestBody NewsDTOReq createRequest) {
        return service.create(createRequest);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update news with given ID", response = NewsDTOResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated news"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 404, message = "News with given ID was not found"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public NewsDTOResp update(@PathVariable Long id, @RequestBody NewsDTOReq updateRequest) {
        updateRequest.setId(id);
        return service.update(updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete news with given ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted news"),
            @ApiResponse(code = 404, message = "Comment with given ID was not found"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @Override
    @GetMapping(value = "/read-by-criteria", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve news corresponding to given criteria", response = NewsDTOResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved requested data"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 404, message = "Found no news matching given criteria"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public List<NewsDTOResp> readByCriteria(@RequestBody NewsDTOReq req) {
        return service.readByCriteria(req);
    }
}
