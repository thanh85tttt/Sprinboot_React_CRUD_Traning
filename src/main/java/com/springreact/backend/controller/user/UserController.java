package com.springreact.backend.controller.user;

import com.springreact.backend.constant.Link;
import com.springreact.backend.entity.Employee;
import com.springreact.backend.services.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * UserController
 * <p>
 * Version 1.0
 * <p>
 * Date: 01-06-2021
 * <p>
 * Copyright By Thanh
 * <p>
 * Modification Logs:
 * DATE             AUTHOR              DESCRIPTION
 * -------------------------------------------------
 * 07-06-2021       ThanhBT11           Create
 */
@CrossOrigin(origins = Link.REACT_URL)
@RestController
@RequestMapping(Link.BASE_URL)
public class UserController {

    private final EmployeeServices employeeServices;

    /**
     * User controller constructor(EmployeeServices)
     *
     * @param employeeServices employeeServices
     */
    @Autowired
    public UserController(EmployeeServices employeeServices) {
        this.employeeServices = employeeServices;
    }

    /**
     * Get employee by email
     *
     * @param email to get employee
     * @return employee by email
     */
    @GetMapping("/user/{email}")
    public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email) {
        //If employee not exist, throw ResourceNotFoundException
        Employee employee = employeeServices.findEmployeeByEmail(email);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}
