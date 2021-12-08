package com.hanshin.shop.exception;

import com.hanshin.shop.response.ResponseError;
import com.hanshin.shop.response.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(AttachmentNotExistException.class)
    public ResponseEntity<Object> attachmentNotExistException(Exception ex, WebRequest webRequest) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> badCredentialException(Exception ex, WebRequest webRequest) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<Object> errorHandler(Exception ex, WebRequest webRequest) {
        log.error("errror : {}, getHeaders : {}", ex.getMessage(), webRequest.getHeaderNames());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
