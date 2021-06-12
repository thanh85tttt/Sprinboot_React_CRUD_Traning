package com.springreact.backend.dto.request;

/**
 * EmployeeSalary
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
public class EmployeeSalary {

    private String employee;

    private String email;

    private Integer salary;

    private String createdDate;

    private String endDate;

    /**
     * Default constructor
     */
    public EmployeeSalary() {
    }

    /**
     * EmployeeSalary constructor(String, Integer)
     *
     * @param employee employee name
     * @param salary   salary
     */
    public EmployeeSalary(String employee, Integer salary) {
        this.employee = employee;
        this.salary = salary;
    }

    /**
     * EmployeeSalary constructor(String, Integer, String, String)
     *
     * @param employee    employee name
     * @param salary      salary
     * @param createdDate issue date of salary
     * @param endDate     end date of salary
     */
    public EmployeeSalary(String employee, Integer salary, String createdDate, String endDate) {
        this.employee = employee;
        this.salary = salary;
        this.createdDate = createdDate;
        this.endDate = endDate;
    }

    /**
     * Get employee name
     *
     * @return employee name
     */
    public String getEmployee() {
        return employee;
    }

    /**
     * Set employee
     *
     * @param employee employee name
     */
    public void setEmployee(String employee) {
        this.employee = employee;
    }

    /**
     * Get salary
     *
     * @return salary
     */
    public Integer getSalary() {
        return salary;
    }

    /**
     * Set salary
     *
     * @param salary salary
     */
    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    /**
     * Get create date
     *
     * @return create date
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * Set create date
     *
     * @param createdDate issue date of salary
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Get end date
     *
     * @return end date
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Set end date
     *
     * @param endDate end date of salary
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
