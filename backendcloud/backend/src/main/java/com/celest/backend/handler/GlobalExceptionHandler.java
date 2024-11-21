package com.celest.backend.handler;

import com.celest.backend.exception.BaseException;
import com.celest.backend.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public Result<Object> handleException(BaseException e) {
        log.error("异常信息:{}",e.getMessage());
        return Result.error(e.getMessage());
    }
}
