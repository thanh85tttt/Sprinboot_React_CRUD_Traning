package com.springreact.backend.dto.request;

/**
 * LoginRequest
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
public class LoginRequest {

    private String username;
    private String password;

    /**
     * Default constructor
     */
    public LoginRequest() {
    }

    /**
     * Login request constructor(String, String)
     *
     * @param username is email of an employee
     * @param password account password
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Get username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username
     *
     * @param username is email of an employee
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get password
     *
     * @return account password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password
     *
     * @param password account password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}











