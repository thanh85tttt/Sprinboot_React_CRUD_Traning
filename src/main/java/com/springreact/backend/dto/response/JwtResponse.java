package com.springreact.backend.dto.response;

import java.util.List;
import java.util.Set;

/**
 * JwtResponse
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
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private List<String> roles;

    /**
     * Default constructor
     */
    public JwtResponse() {
    }

    /**
     * JwtResponse constructor(String, Long, String, List<String>)
     *
     * @param accessToken jwt token
     * @param id          employee id
     * @param username    is email of an employee
     * @param roles       employee role
     */
    public JwtResponse(String accessToken, Long id, String username, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    /**
     * Get access token
     *
     * @return jwt token
     */
    public String getAccessToken() {
        return token;
    }

    /**
     * Set access token
     *
     * @param accessToken jwt token
     */
    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    /**
     * Get token type
     *
     * @return type
     */
    public String getTokenType() {
        return type;
    }

    /**
     * Set token type
     *
     * @param tokenType is Bearer token type
     */
    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    /**
     * Get id
     *
     * @return employee id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id employee id
     */
    public void setId(Long id) {
        this.id = id;
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
     * Get roles
     *
     * @return role of an employee
     */
    public List<String> getRoles() {
        return roles;
    }
}
