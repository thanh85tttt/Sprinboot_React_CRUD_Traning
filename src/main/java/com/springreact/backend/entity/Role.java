package com.springreact.backend.entity;

import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Role
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
@Table(name = "role")
@Where(clause = "active != 0")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    private String role;

    @Column(name = "createdDate")
    private String createdDate;

    @Column(name = "active")
    private boolean active;

    /**
     * Default constructor
     */
    public Role() {
    }

    /**
     * Role constructor
     *
     * @param role        role name
     * @param createdDate issue date of role
     */
    public Role(String role, String createdDate) {
        this.role = role;
        this.createdDate = createdDate;
    }

    /**
     * Get id
     *
     * @return role id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id role id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get role
     *
     * @return role name
     */
    public String getRole() {
        return role;
    }

    /**
     * Set role
     *
     * @param role role name
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Get create date
     *
     * @return createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * Set create date
     *
     * @param createdDate issue date of role
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Check role is available or not
     *
     * @return active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set active
     *
     * @param active check role is available or not
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
