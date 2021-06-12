package com.springreact.backend.dto.request;

/**
 * EmployeeRoleAndDepartment
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
public class EmployeeRoleAndDepartment {

    private String fullName;
    private String email;
    private String department;
    private String role;

    /**
     * Default constructor
     */
    public EmployeeRoleAndDepartment() {
    }

    /**
     * EmployeeRoleAndDepartment constructor(String, String,  String, String)
     *
     * @param fullName   employee name
     * @param email      employee email
     * @param department department code
     * @param role       role code
     */
    public EmployeeRoleAndDepartment(String fullName, String email, String department, String role) {
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.role = role;
    }

    /**
     * EmployeeRoleAndDepartment constructor(String, String)
     *
     * @param email    employee email
     * @param fullName employee name
     */
    public EmployeeRoleAndDepartment(String email, String fullName) {
        this.email = email;
        this.fullName = fullName;
    }

    /**
     * EmployeeRoleAndDepartment constructor(String, String, String)
     *
     * @param email      employee email
     * @param fullName   employee name
     * @param department department code
     */
    public EmployeeRoleAndDepartment(String email, String fullName, String department) {
        this.email = email;
        this.fullName = fullName;
        this.department = department;
    }

    /**
     * Get fullName
     *
     * @return employee name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Set fullName
     *
     * @param fullName employee name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Get department
     *
     * @return department code
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Set department
     *
     * @param department department code
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Get role
     *
     * @return role code
     */
    public String getRole() {
        return role;
    }

    /**
     * Set role
     *
     * @param role role code
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Get email
     *
     * @return employee email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email
     *
     * @param email employee email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
