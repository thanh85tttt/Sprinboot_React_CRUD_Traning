package com.springreact.backend.security.service;

import com.springreact.backend.entity.Employee;
import com.springreact.backend.exception.ResourceNotFoundException;
import com.springreact.backend.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserDetailsServiceImpl
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
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeesRepository employeesRepository;

    /**
     * UserDetailsServiceImpl constructor(EmployeesRepository)
     *
     * @param employeesRepository employeesRepository
     */
    @Autowired
    public UserDetailsServiceImpl(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    /**
     * Load user by username
     *
     * @param username to load user
     * @return UserDetailsImpl base on employee information
     * @throws UsernameNotFoundException throw UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Employee employee = employeesRepository.findByEmail(username)
                .orElseThrow(() -> new
                        ResourceNotFoundException("Employee no longer existed" +
                        " with username " + username)
                );

        return UserDetailsImpl.build(employee);
    }

}










