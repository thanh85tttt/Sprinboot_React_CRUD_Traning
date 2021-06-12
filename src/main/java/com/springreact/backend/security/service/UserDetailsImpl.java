package com.springreact.backend.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springreact.backend.entity.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * UserDetailsImpl
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
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Long id;

    private final String username;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * UserDetailsImpl constructor(Long, String, String, Collection<? extends GrantedAuthority>)
     *
     * @param id          employee id
     * @param username    is email of employee
     * @param password    account password
     * @param authorities authorities employee
     */
    public UserDetailsImpl(Long id, String username, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Build new UserDetails
     *
     * @param employee get information to build UserDetails
     * @return UserDetailsImpl
     */
    public static UserDetailsImpl build(Employee employee) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(employee.getRole().getRole()));

        return new UserDetailsImpl(
                employee.getId(),
                employee.getEmail(),
                employee.getPassword(),
                authorities
        );

    }

    /**
     * Get collection of authorities to granted authority
     *
     * @return authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
     * Get password
     *
     * @return account password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Get username
     *
     * @return username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Check account non expired
     *
     * @return true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Check account non locked
     *
     * @return true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Check credentials non expired
     *
     * @return true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Check account is enable
     *
     * @return true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Check equals user and one object
     *
     * @param o to check equals
     * @return true if that object and user is the same and false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
