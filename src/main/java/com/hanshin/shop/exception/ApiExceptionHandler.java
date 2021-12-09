package com.hanshin.shop.exception;

import com.hanshin.shop.exception.custom.AttachmentNotExistException;
import com.hanshin.shop.exception.custom.CustomJwtRuntimeException;
import com.hanshin.shop.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(AttachmentNotExistException.class)
    public ResponseEntity<Object> attachmentNotExistException(AttachmentNotExistException e) {

        log.error("AttachmentNotExistException ",e);

        CommonResponse response = CommonResponse.builder()
                .code(ErrorCode.ATTACHMENT_NOT_EXIST.getCode())
                .message(e.getMessage())
                .status(ErrorCode.ATTACHMENT_NOT_EXIST.getStatus())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> badCredentialException(BadCredentialsException e) {

        log.error("badCredentialException ",e);

        CommonResponse response = CommonResponse.builder()
                .code(ErrorCode.AUTHENTICATION_FAILED.getCode())
                .message(e.getMessage())
                .status(ErrorCode.AUTHENTICATION_FAILED.getStatus())
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomJwtRuntimeException.class)
    protected ResponseEntity<CommonResponse> handleJwtException(CustomJwtRuntimeException e) {

        log.info("handleJwtException", e);

        CommonResponse response = CommonResponse.builder()
                .code(ErrorCode.INVALID_JWT_TOKEN.getCode())
                .message(ErrorCode.INVALID_JWT_TOKEN.getMessage())
                .status(ErrorCode.INVALID_JWT_TOKEN.getStatus())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<Object> errorHandler(Exception e) {

        log.error("errorHandler ", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
