package com.mjc.school.service.validator;

public interface Validator<T> {
    void validate(T obj, String reqType);
}
