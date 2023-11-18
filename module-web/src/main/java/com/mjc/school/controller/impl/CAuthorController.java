package com.mjc.school.controller.impl;

import com.mjc.school.controller.AuthorController;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.author.AuthorDTOReq;
import com.mjc.school.service.dto.author.AuthorDTOResp;
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

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(produces = "application/json", tags = "Authors", description = "CRUD operations for authors")
public class CAuthorController implements AuthorController {
    @Autowired
    AuthorService service;

    @Override
    @GetMapping(value = "/authors")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "List all authors.",
            response = PageDTOResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved requested data"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public PageDTOResp<AuthorDTOResp> readAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createDate") String sortBy,
            @RequestParam(name = "order", required = false, defaultValue = "desc") String order) {
        var pageReq = new PageDTOReq(page, size, sortBy, order);
        return service.readAll(pageReq);
    }

    @Override
    @PostMapping(value = "/authors/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create an author", response = AuthorDTOResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created an author"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public AuthorDTOResp create(@RequestBody AuthorDTOReq createRequest) {
        return service.create(createRequest);
    }

    @Override
    @GetMapping(value = "/authors/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve an author by ID", response = AuthorDTOResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved requested data"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 404, message = "Author with given ID was not found"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public AuthorDTOResp readById(@PathVariable Long id) {
        return service.readById(id);
    }

    @Override
    @PatchMapping(value = "/authors/{id:\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update a author with given ID", response = AuthorDTOResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the author"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 404, message = "Author with given ID was not found"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public AuthorDTOResp update(@PathVariable Long id, @RequestBody AuthorDTOReq updateRequest) {
        updateRequest.setId(id);
        return service.update(updateRequest);
    }

    @Override
    @DeleteMapping(value = "/authors/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete an author with given ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted the author"),
            @ApiResponse(code = 404, message = "Author with given ID was not found"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @Override
    @GetMapping(value = "/news/{id:\\d+}/author")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Read an author by news ID", response = AuthorDTOResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved requested data"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 404, message = "News with given ID was not found"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public AuthorDTOResp readByNewsId(@PathVariable Long id) {
        return service.readAuthorByNewsId(id);
    }
}
