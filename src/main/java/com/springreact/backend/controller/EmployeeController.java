package com.springreact.backend.controller;

import com.springreact.backend.constant.Link;
import com.springreact.backend.constant.Message;
import com.springreact.backend.dto.request.EmployeeRoleAndDepartment;
import com.springreact.backend.entity.Employee;
import com.springreact.backend.exception.ResourceNotFoundException;
import com.springreact.backend.services.EmployeeServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * EmployeeController
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
@CrossOrigin(origins = Link.REACT_URL)
@RestController
@RequestMapping(Link.BASE_URL)
public class EmployeeController {

    private final EmployeeServices employeeServices;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * EmployeeController constructor(EmployeeServices)
     * @param employeeServices employeeServices
     */
    @Autowired
    public EmployeeController(EmployeeServices employeeServices) {
        this.employeeServices = employeeServices;
    }

    /**
     * Get All Employees
     *
     * @return employee list
     */
    @GetMapping(Link.EMPLOYEE_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employeeList = null;

        try {
            employeeList = employeeServices.findAllEmployeeOrderByIdDesc();
        } catch (NullPointerException e) {
            logger.error("An error occur: " + e.getMessage());
            throw new ResourceNotFoundException(Message.NOT_EXIST_EMPLOYEE_LIST);
        }
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    /**
     * Create new Employee
     *
     * @param employee contains employee to create
     * @return message if create success or not
     */
    @PostMapping(Link.EMPLOYEE_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {

        String message = employeeServices.processCreateEmployee(employee);
        HttpStatus status = HttpStatus.CREATED;

        switch (message) {
            case Message.CREATE_SUCCESS:
                break;
            case Message.EXIST_EMAIL:
            case Message.EXIST_PHONE_NUMBER:
                status = HttpStatus.BAD_REQUEST;
                break;
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Get Employee by employee id
     *
     * @param id to find employee
     * @return employee by id
     */
    @GetMapping(Link.EMPLOYEE_ID_PATH_URL)
    public ResponseEntity<Employee> getEmployeeById(
            @PathVariable(name = "id") Long id) {
        Employee employee = employeeServices.findEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    /**
     * Get employee by email
     *
     * @param email to get employee
     * @return employee by email
     */
    @GetMapping(Link.EMPLOYEE_EMAIL_PATH_URL)
    public ResponseEntity<Employee> getEmployeeByEmail(
            @PathVariable(name = "email") String email) {
        Employee employee = employeeServices.findEmployeeByEmail(email);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    /**
     * Update employee by employee id
     *
     * @param employeeUpdate contains update information
     * @param id             to find and update employee
     * @return message if update success or not
     */
    @PutMapping(Link.EMPLOYEE_ID_PATH_URL)
    public ResponseEntity<String> updateEmployee(@RequestBody Employee employeeUpdate,
                                                 @PathVariable Long id) {

        String message = employeeServices.processUpdateEmployee(employeeUpdate, id);
        HttpStatus status = HttpStatus.OK;

        switch (message) {
            case Message.UPDATE_SUCCESS:
                break;
            case Message.EXIST_PHONE_NUMBER:
            case Message.EXIST_EMAIL:
                status = HttpStatus.BAD_REQUEST;
                break;
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Delete employee by id
     *
     * @param id to delete employee
     * @return message if delete success or not
     */
    @DeleteMapping(Link.EMPLOYEE_ID_PATH_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        String message = employeeServices.processDeleteEmployee(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Get List role and department of all employees
     *
     * @return list role and department of all employees
     */
    @GetMapping(Link.EMPLOYEE_ROLE_DEPARTMENT_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EmployeeRoleAndDepartment>> getEmployeeRoleAndDepartment() {
        List<EmployeeRoleAndDepartment> employeeRoleAndDepartmentList =
                employeeServices.listEmployeeRoleAndDepartment();
        return new ResponseEntity<>(employeeRoleAndDepartmentList, HttpStatus.OK);
    }

    /**
     * Update role and department of an employee
     *
     * @param employeeRoleAndDepartmentUpdate contains update information
     * @param email                           find employee to update role and department
     * @return message if update success or not
     */
    @PutMapping(Link.EMPLOYEE_ROLE_DEPARTMENT_EMAIL_PATH_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateEmployeeRoleAndDepartment(
            @RequestBody EmployeeRoleAndDepartment employeeRoleAndDepartmentUpdate,
            @PathVariable String email) {

        String message = employeeServices.processUpdateEmployeeRoleAndDepartment(
                employeeRoleAndDepartmentUpdate,
                email
        );
        HttpStatus status = HttpStatus.OK;

        switch (message) {
            case Message.NOT_EXIST_ROLE:
            case Message.NOT_EXIST_DEPARTMENT:
                status = HttpStatus.BAD_REQUEST;
                break;
            case Message.UPDATE_ROLE_DEPARTMENT_SUCCESS:
                break;
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Get role and department of employee by employee email
     *
     * @param email to find employee
     * @return role and department of employee
     */
    @GetMapping(Link.EMPLOYEE_ROLE_DEPARTMENT_EMAIL_PATH_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeRoleAndDepartment> getEmployeeRoleAndDepartmentByEmail(
            @PathVariable String email) {

        EmployeeRoleAndDepartment employeeRoleAndDepartment =
                employeeServices.findEmployeeRoleAndDepartmentByEmail(email);

        return new ResponseEntity<>(employeeRoleAndDepartment, HttpStatus.OK);
    }

    /**
     * Find distinct address of all employees
     *
     * @return distinct address list
     */
    @GetMapping(Link.DISTINCT_ADDRESS_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<String>> getDistinctAddress() {
        List<String> addressList = employeeServices.findDistinctAddress();
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

    /**
     * Search employees by keyword
     *
     * @param keyword to search employees contain information match with keyword
     * @return employee list match with keyword
     */
    @GetMapping(Link.SEARCH_BY_KEYWORD_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Employee>> searchEmployees(@PathVariable String keyword) {

        List<Employee> employeeList = employeeServices.processSearchEmployees(keyword);

        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    /**
     * Search employees by keyword
     *
     * @param keyword to search employees contain information match with keyword
     * @return employee list match with keyword
     */
    @GetMapping(Link.SEARCH_ROLE_DEPARTMENT_BY_KEYWORD_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EmployeeRoleAndDepartment>> searchEmployeesRoleAndDepartment(
            @PathVariable String keyword) {

        List<EmployeeRoleAndDepartment> employeeList = employeeServices
                .processSearchEmployeeRoleAndDepartment(keyword);

        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    /**
     * Get employee image
     *
     * @param email to get current user image
     * @return image link
     */
    @GetMapping(Link.LOAD_IMAGE_URL)
    public ResponseEntity<String> getEmployeeImage(@PathVariable String email) {
        String image = employeeServices.findEmployeeImageByEmail(email);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

}


















