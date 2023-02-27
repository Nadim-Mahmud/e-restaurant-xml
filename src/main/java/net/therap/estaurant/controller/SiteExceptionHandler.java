package net.therap.estaurant.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author nadimmahmud
 * @since 1/30/23
 */
@ControllerAdvice
public class SiteExceptionHandler {

    private static final String ERROR_VIEW = "error";

//    @ExceptionHandler(value = Exception.class)
//    public String handleError() {
//        return ERROR_VIEW;
//    }
}
