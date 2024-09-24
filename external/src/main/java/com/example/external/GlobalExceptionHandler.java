package com.example.external;

import com.example.core.exception.AppError;
import com.example.core.exception.ErrorCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ErrorCodeException.class)
    public ResponseEntity<AppError> catchResourceNotFoundException(ErrorCodeException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(e.getHttpStatus(), e.getMessage()), HttpStatus.valueOf(e.getHttpStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppError> catchResourceNotFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(500, e.getMessage()), HttpStatus.valueOf(500));
    }
}
