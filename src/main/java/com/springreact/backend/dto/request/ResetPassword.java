package com.springreact.backend.dto.request;

/**
 * ResetPassword
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
public class ResetPassword {
    private String password;
    private String confirmPassword;

    /**
     * Default constructor
     */
    public ResetPassword() {
    }

    /**
     * Reset password constructor(String, String)
     *
     * @param password        reset password
     * @param confirmPassword confirm password is match with reset password
     */
    public ResetPassword(String password, String confirmPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    /**
     * Get password
     *
     * @return reset password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password
     *
     * @param password reset password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get confirm password
     *
     * @return confirm password
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Set confirm password
     *
     * @param confirmPassword confirm password is match with reset password
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
