package com.springreact.backend.dto.request;

/**
 * DepartmentName
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
public class DepartmentName {

    private String department;
    private String fullName;

    /**
     * Default constructor
     */
    public DepartmentName() {
    }

    /**
     * DepartmentName constructor(String, String)
     *
     * @param department department code
     * @param fullName   department name
     */
    public DepartmentName(String department, String fullName) {
        this.department = department;
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
     * Get full name
     *
     * @return department name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Set full name
     *
     * @param fullName department name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
