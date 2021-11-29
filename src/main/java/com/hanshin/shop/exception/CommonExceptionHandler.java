package com.hanshin.shop.exception;

import com.hanshin.shop.response.ResponseError;
import com.hanshin.shop.response.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler
    public String errorHandler(Exception e, Model model) {

        log.error("Exception ... " + e);
        model.addAttribute("exception", e);
        return "error/error_page";
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity notValidParamExceptionHandling(IOException error, HttpStatus httpStatus, HttpServletRequest request) {
        log.error("{}. uri: {}?{}", error.getMessage(), request.getRequestURI(), request.getQueryString());
        ResponseObject responseEntity = setResponseError(error, httpStatus);
        return new ResponseEntity<>(responseEntity, HttpStatus.BAD_REQUEST);
    }

    private ResponseObject setResponseError(IOException error, HttpStatus httpStatus) {
        ResponseObject responseEntity = new ResponseObject();
        ResponseError responseError = new ResponseError();
        responseError.setStatus(httpStatus.toString());
        responseError.setTitle(error.getMessage());
        responseError.setDetail(error.getStackTrace().toString());
        responseEntity.addError(responseError);
        return responseEntity;
    }

}
