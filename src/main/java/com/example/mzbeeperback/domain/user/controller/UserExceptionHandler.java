package com.example.mzbeeperback.domain.user.controller;

import com.example.mzbeeperback.global.exception.ErrorResult;
import com.example.mzbeeperback.global.exception.IllegalLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult noUserHandler(IllegalStateException e) { // db에 회원이 없음
        log.error("no user stored", e);
        return new ErrorResult("NO USER", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult failedLoginHandler(IllegalLoginException e) { // 비밀번호가 맞지 않음
        log.error("failed login");
        return new ErrorResult("login failed", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }
}
