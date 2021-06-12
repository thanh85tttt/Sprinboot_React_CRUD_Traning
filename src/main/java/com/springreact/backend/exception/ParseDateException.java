package com.springreact.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ParseDateException
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
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ParseDateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * ParseDateException constructor(String)
     * @param message exception message
     */
    public ParseDateException(String message) {
        super(message);
    }
}
