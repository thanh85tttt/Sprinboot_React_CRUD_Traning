package com.springreact.backend.entity;

import javax.persistence.*;

/**
 * Salary
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
@Entity
@Table(name = "salary")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "createdDate")
    private String createdDate;

    @Column(name = "endDate")
    private String endDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    private Employee employee;

    @Column(name = "active")
    private boolean active;

    /**
     * Salary constructor
     */
    public Salary() {
    }

    /**
     * Salary constructor
     * @param salary employee salary
     * @param createdDate issue date of salary
     * @param endDate end date of salary
     */
    public Salary(Integer salary, String createdDate, String endDate) {
        this.salary = salary;
        this.createdDate = createdDate;
        this.endDate = endDate;
    }

    /**
     * Get id
     * @return salary id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * @param id salary id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get salary
     * @return salary
     */
    public Integer getSalary() {
        return salary;
    }

    /**
     * Set salary
     * @param salary employee salary
     */
    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    /**
     * Get create date
     * @return createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * Set create date
     * @param createdDate issue date of salary
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * get end date
     * @return end date of salary
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Set end date
     * @param endDate end date of salary
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Get employee
     * @return employee of an salary
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Set employee
     * @param employee employee of an salary
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Check salary is available or not
     * @return active or inactive
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set active
     * @param active check salary is active or not
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
