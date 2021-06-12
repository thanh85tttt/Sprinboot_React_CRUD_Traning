package com.springreact.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ExceptionHandling
 * <p>
 * Version 1.0
 * <p>
 * Date: 30-05-2021
 * <p>
 * Copyright By Thanh
 * <p>
 * Modification Logs:
 * DATE             AUTHOR              DESCRIPTION
 * -------------------------------------------------
 * 07-06-2021       ThanhBT11           Create
 */
@RestControllerAdvice
public class ExceptionHandling {

    /**
     * Custom handle exception to throw ResourceNotFoundException to frontend
     * @param exception ResourceNotFoundException
     * @return exception response to frontend
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionRestResponse handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ExceptionRestResponse(500, exception.getMessage());
    }

    /**
     * Custom handle exception to throw ParseDateException to frontend
     * @param exception ParseDateException
     * @return exception response to frontend
     */
    @ExceptionHandler(ParseDateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionRestResponse handleParseDateException(ParseDateException exception) {
        return new ExceptionRestResponse(500, exception.getMessage());
    }

    /**
     * Custom handle exception to throw NumberFormatException to frontend
     * @param exception NumberFormatException
     * @return exception response to frontend
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionRestResponse handleNullPointerException(NullPointerException exception) {
        return new ExceptionRestResponse(500, exception.getMessage());
    }

    /**
     * Custom handle exception to throw NumberFormatException to frontend
     * @param exception NumberFormatException
     * @return exception response to frontend
     */
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionRestResponse handleNumberFormatException(NumberFormatException exception) {
        return new ExceptionRestResponse(500, exception.getMessage());
    }
}
