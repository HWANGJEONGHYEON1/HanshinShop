package com.hanshin.shop.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public String errorHandler(Exception e, Model model) {
        log.error("Exception ... " + ex.getMessage());
        model.addAttribute("exception", ex);
        log.error(model);

        return "error_page";
    }
}
