package com.springreact.backend.dto.request;

/**
 * ForgotPassword
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
public class ForgotPassword {

    private String token;
    private String email;

    /**
     * Default constructor
     */
    public ForgotPassword() {
    }

    /**
     * Forgot password constructor(String, String)
     *
     * @param token check token from email is match with reset password token in database
     * @param email to send link contains token
     */
    public ForgotPassword(String token, String email) {
        this.token = token;
        this.email = email;
    }

    /**
     * Get token
     *
     * @return reset password token
     */
    public String getToken() {
        return token;
    }

    /**
     * Set token
     *
     * @param token reset password token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Get email
     *
     * @return email to send a link contains reset password token
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email
     *
     * @param email email to send a link contains reset password token
     */
    public void setEmail(String email) {
        this.email = email;
    }
}










