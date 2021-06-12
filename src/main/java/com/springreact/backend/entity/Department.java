package com.springreact.backend.entity;

import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Department
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
@Table(name = "department")
@Where(clause = "active != 0")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department")
    private String department;

    @Column(name = "createdDate")
    private String createdDate;

    @Column(name = "FullName")
    private String fullName;

    @Column(name = "active")
    private boolean active;

    /**
     * Default constructor
     */
    public Department() {
    }

    /**
     * Department constructor
     *
     * @param department  department code
     * @param createdDate issue date of department
     * @param fullName    name of department
     */
    public Department(String department, String createdDate, String fullName) {
        this.department = department;
        this.createdDate = createdDate;
        this.fullName = fullName;
    }

    /**
     * Get Id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id department id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get department
     *
     * @return department
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
     * Get created date
     *
     * @return createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * Set created date
     *
     * @param createdDate issue date of department
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Get full name
     *
     * @return full name
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

    /**
     * Check is employee is active or not
     *
     * @return active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set active
     *
     * @param active is department available or not
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}