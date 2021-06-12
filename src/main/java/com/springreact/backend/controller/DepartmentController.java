package com.springreact.backend.controller;

import com.springreact.backend.constant.Link;
import com.springreact.backend.constant.Message;
import com.springreact.backend.dto.request.DepartmentName;
import com.springreact.backend.entity.Department;
import com.springreact.backend.exception.ResourceNotFoundException;
import com.springreact.backend.services.DepartmentServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * DepartmentController
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
public class DepartmentController {

    private final DepartmentServices departmentServices;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * DepartmentController constructor(DepartmentServices)
     * @param departmentServices departmentServices
     */
    public DepartmentController(DepartmentServices departmentServices) {
        this.departmentServices = departmentServices;
    }

    /**
     * Get All Departments
     *
     * @return department list
     */
    @GetMapping(Link.DEPARTMENT_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = null;

        try {
            departments = departmentServices.findAllByOrderByDepartmentAsc();
        } catch (NullPointerException e) {
            logger.error("An error occur: " + e.getMessage());
            throw new ResourceNotFoundException(Message.NOT_EXIST_DEPARTMENT_LIST);
        }
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    /**
     * Create new Department
     *
     * @param departmentName contains department and its name
     * @return message if create success or not
     */
    @PostMapping(Link.DEPARTMENT_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createDepartment(@RequestBody DepartmentName departmentName) {
        String message = departmentServices.processCreateDepartment(departmentName);
        HttpStatus status = HttpStatus.CREATED;

        switch (message) {
            case Message.EXIST_DEPARTMENT:
            case Message.EXIST_DEPARTMENT_NAME:
                status = HttpStatus.BAD_REQUEST;
                break;
            case Message.CREATE_SUCCESS:
                break;
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Delete department by department id
     *
     * @param id to check is there any employees exist and process delete department
     * @return message if delete success or not
     */
    @DeleteMapping(Link.DEPARTMENT_ID_PATH_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
        Boolean isEmployeeExistByDepartment =
                departmentServices.isEmployeeExistByDepartment(id);
        String message = null;
        HttpStatus status = HttpStatus.OK;

        //Cannot delete if there are any employees in department
        if (isEmployeeExistByDepartment) {
            message = Message.EXIST_EMPLOYEE_BY_DEPARTMENT;
            status = HttpStatus.BAD_REQUEST;
        } else {
            message = departmentServices.processDeleteDepartment(id);
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Get department by department id
     *
     * @param id to get department
     * @return department by id
     */
    @GetMapping(Link.DEPARTMENT_ID_PATH_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Department department = departmentServices.findDepartmentById(id);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    /**
     * Update department
     *
     * @param departmentUpdate contains updated information
     * @param id               to update department
     * @return message if update success or not
     */
    @PutMapping(Link.DEPARTMENT_ID_PATH_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateDepartment(@RequestBody Department departmentUpdate,
                                                   @PathVariable(name = "id") Long id) {

        //Process update service
        String message = departmentServices.processUpdateDepartment(
                departmentUpdate,
                id
        );
        HttpStatus status = HttpStatus.OK;

        switch (message) {
            case Message.EXIST_DEPARTMENT_NAME:
            case Message.EXIST_DEPARTMENT:
                status = HttpStatus.BAD_REQUEST;
                break;
            case Message.UPDATE_SUCCESS:
                break;
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Search employees by keyword
     *
     * @param keyword to search employees contain information match with keyword
     * @return employee list match with keyword
     */
    @GetMapping(Link.SEARCH_DEPARTMENT_BY_KEYWORD_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Department>> searchDepartments(@PathVariable String keyword) {

        List<Department> departmentList= departmentServices.processSearchDepartments(keyword);

        return new ResponseEntity<>(departmentList, HttpStatus.OK);
    }

}










