package com.example.demo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "Пользователь не найден"),
    USER_EMAIL_TAKEN(HttpStatus.CONFLICT, "Пользователь с таким email уже существует"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Ошибка валидации"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Требуется аутентификация"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Доступ запрещён");

    private final HttpStatus status;
    private final String message;
}