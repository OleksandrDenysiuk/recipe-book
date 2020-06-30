package com.portfolio.recipebook.controller.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        Map<String, String> errorsMap = new HashMap<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError) error;
            errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return errorsMap;
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Map<String, String> handleBindException(BindException e,
                                            HttpServletRequest request,
                                            HttpServletResponse response)
    {
        Map<String, String> errorsMap = new HashMap<>();

        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError) error;
            errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return errorsMap;
    }
}
