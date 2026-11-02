package com.example.demo.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ApiException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Map<String, Object> details;

    public ApiException(ErrorCode errorCode) {
        this(errorCode, new HashMap<>());
    }

    public ApiException(ErrorCode errorCode, Map<String, Object> details) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.details = details == null ? new HashMap<>() : details;
    }

    public ApiException addDetail(String key, Object value) {
        this.details.put(key, value);
        return this;
    }
}