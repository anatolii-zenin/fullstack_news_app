package com.mjc.school.service.validator.implementation;

import com.mjc.school.service.PaginationCapableService;
import com.mjc.school.service.exception.BadRequestException;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.validator.Validator;

public abstract class BaseValidator<Req> implements Validator<Req> {
    protected <Service extends PaginationCapableService<?, ?, Long>> void validateId(Long id, Service service) {
        if (id == null) {
            throw new BadRequestException("null value was provided for an ID");
        }
        if (service.readById(id) == null) {
            throw new NotFoundException("\tentity with id " + id + " does not exist.\n");
        }
    }

    protected void validateNullId(Long id) {
        if (id != null) {
            throw new BadRequestException("ID should not be provided for a new entry");
        }
    }

    protected void validateString(String string, int stringLenFrom, int stringLenTo) {
        if (string == null) {
            throw new BadRequestException("null string value in a required field was provided");
        }
        if (!validateLength(string.length(), stringLenFrom, stringLenTo)) {
            throw new BadRequestException("field length should be between " + stringLenFrom + " and " +
                    stringLenTo + " characters. Provided length: " + string.length() + ".");
        }
    }

    private boolean validateLength(int value, int from, int to) {
        return value >= from && value <= to;
    }
}