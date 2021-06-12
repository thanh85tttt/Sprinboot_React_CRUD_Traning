package com.springreact.backend.dto.request;

import java.util.Set;

/**
 * SignupRequest
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
public class SignupRequest {

    private String username;

    private Set<String> role;

    private String password;

    private String confirmPassword;

    /**
     * Default constructor
     */
    public SignupRequest() {
    }

    /**
     * SignupRequest constructor(String, String, String)
     *
     * @param username        is email of an employee
     * @param password        account password
     * @param confirmPassword confirm password is match with account password
     */
    public SignupRequest(String username, String password, String confirmPassword) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
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
     * get password
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

    /**
     * Get role
     *
     * @return role
     */
    public Set<String> getRole() {
        return this.role;
    }

    /**
     * Set role
     *
     * @param role employee role
     */
    public void setRole(Set<String> role) {
        this.role = role;
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
     * @param confirmPassword confirm password is match with account password
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
