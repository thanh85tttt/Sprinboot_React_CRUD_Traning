package com.springreact.backend.dto.response;

/**
 * MessageResponse
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
public class MessageResponse {

    private String message;

    /**
     * MessageResponse constructor(String)
     *
     * @param message message response to client side
     */
    public MessageResponse(String message) {
        this.message = message;
    }

    /**
     * Get message
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set message
     *
     * @param message message response to client side
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
