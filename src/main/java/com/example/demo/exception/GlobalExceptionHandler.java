package com.example.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String, Object>> handleApiException(ApiException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", ex.getErrorCode().name());
        body.put("message", ex.getErrorCode().getMessage());
        body.putAll(ex.getDetails());
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", ErrorCode.FORBIDDEN.name());
        body.put("message", ErrorCode.FORBIDDEN.getMessage());
        return ResponseEntity.status(ErrorCode.FORBIDDEN.getStatus()).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fields = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> fields.putIfAbsent(err.getField(), err.getDefaultMessage()));

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", ErrorCode.VALIDATION_ERROR.name());
        body.put("message", ErrorCode.VALIDATION_ERROR.getMessage());
        body.put("fields", fields);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOther(Exception ex) {
        String traceId = UUID.randomUUID().toString();
        log.error("Unhandled exception, traceId={}", traceId, ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", ErrorCode.INTERNAL_ERROR.name());
        body.put("message", ErrorCode.INTERNAL_ERROR.getMessage());
        body.put("traceId", traceId);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}