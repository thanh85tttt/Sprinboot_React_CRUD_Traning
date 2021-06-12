package com.springreact.backend.entity;

import javax.persistence.*;

/**
 * PrefixPhoneNumber
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
@Table(name = "prefix")
public class PrefixPhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "title")
    private String title;

    /**
     * Default constructor
     */
    public PrefixPhoneNumber() {
    }

    /**
     * Prefix constructor
     *
     * @param value phone prefix
     * @param title phone prefix title
     */
    public PrefixPhoneNumber(String value, String title) {
        this.value = value;
        this.title = title;
    }

    /**
     * Get id
     *
     * @return prefix id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id prefix id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get value
     *
     * @return prefix value
     */
    public String getValue() {
        return value;
    }

    /**
     * Set value
     *
     * @param value prefix value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Get title
     *
     * @return prefix title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title
     *
     * @param title prefix title
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
