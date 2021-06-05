package com.hanshin.shop.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler
    public String errorHandler(Exception e, Model model) {

        log.error("Exception ... " + e);
        model.addAttribute("exception", e);
        return "error/error_page";
    }
}
