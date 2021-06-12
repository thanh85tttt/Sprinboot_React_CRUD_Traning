package com.springreact.backend.dto.request;

/**
 * RoleName
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
public class RoleName {

    private String role;

    /**
     * Default constructor
     */
    public RoleName() {
    }

    /**
     * RoleName constructor(String)
     * @param role role of an employee
     */
    public RoleName(String role) {
        this.role = role;
    }

    /**
     * Get role
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * Set role
     * @param role role of an employee
     */
    public void setRole(String role) {
        this.role = role;
    }
}
