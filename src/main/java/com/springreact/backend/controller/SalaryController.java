package com.springreact.backend.controller;

import com.springreact.backend.constant.Link;
import com.springreact.backend.constant.Message;
import com.springreact.backend.dto.request.EmployeeSalary;
import com.springreact.backend.entity.Salary;
import com.springreact.backend.exception.ResourceNotFoundException;
import com.springreact.backend.services.SalaryServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * SalaryController
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
public class SalaryController {

    private final SalaryServices salaryServices;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * SalaryController constructor(SalaryServices)
     *
     * @param salaryServices salaryServices
     */
    public SalaryController(SalaryServices salaryServices) {
        this.salaryServices = salaryServices;
    }

    /**
     * Get latest salary of all employees
     *
     * @return list of latest salaries
     */
    @GetMapping(Link.SALARY_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EmployeeSalary>> getAllLatestSalaries() {
        List<EmployeeSalary> employeeSalaryList;

        try {
            employeeSalaryList = salaryServices
                    .findAllSalaryOrderByIdDesc();
        } catch (NullPointerException | ParseException e) {
            logger.error("An error occur: " + e.getMessage());
            throw new ResourceNotFoundException(Message.NOT_EXIST_SALARY_LIST);
        }

        return new ResponseEntity<>(employeeSalaryList, HttpStatus.OK);
    }

    /**
     * Create new Salary by employee email
     *
     * @param salary contains salary information
     * @param email  to create new salary for an employee
     * @return message if create success or not
     * @throws ParseException throw parse exception
     */
    @PostMapping(Link.SALARY_EMAIL_PATH_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createNewSalary(@RequestBody EmployeeSalary salary,
                                                  @PathVariable String email)
            throws ParseException {

        String message = salaryServices.processCreateNewSalary(salary, email);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Check if salary exist by email and created
     *
     * @param email       to check exist salary
     * @param createdDate to check exist salary
     * @return true if salary exist, false if not
     * @throws ParseException throw parse exception
     */
    @GetMapping(Link.SALARY_EMAIL_CREATED_DATE_PATH_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> isSalaryExistByEmailAndCreatedDate(
            @PathVariable(name = "email") String email,
            @PathVariable(name = "createdDate") String createdDate)
            throws ParseException {

        Boolean isExistSalaryByEmployeeEmailAndCreatedDate =
                salaryServices.checkExistSalaryByEmployeeEmailAndCreatedDate(
                        email, createdDate);

        return new ResponseEntity<>(isExistSalaryByEmployeeEmailAndCreatedDate,
                HttpStatus.OK);
    }

    /**
     * Get Salary by id
     *
     * @param id to get salary
     * @return salary by salary id
     */
    @GetMapping(Link.SALARY_ID_PATH_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Salary> getSalaryById(@PathVariable Long id) {
        Salary salary = salaryServices.findSalaryById(id);
        return new ResponseEntity<>(salary, HttpStatus.OK);
    }

    /**
     * Find All salary by email
     *
     * @param email to find salary list
     * @return salary list base on email
     */
    @GetMapping(Link.SALARY_BY_EMPLOYEE_EMAIL_PATH_URL)
    public ResponseEntity<List<Salary>> finAllSalaryByEmail(@PathVariable String email) {
        List<Salary> salaryList = salaryServices.findAllSalaryByEmail(email);
        return new ResponseEntity<>(salaryList, HttpStatus.OK);
    }

    /**
     * Get salary by email and create date
     *
     * @param email       to get salary
     * @param createdDate to get salary
     * @return salary base on email and create date
     */
    @GetMapping(Link.SALARY_EMPLOYEE_EMAIL_CREATED_DATE_PATH_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Salary> getSalaryByEmailAndCreatedDate(
            @PathVariable String email,
            @PathVariable String createdDate) {
        Salary salary = salaryServices.findByEmployee_EmailAndCreatedDate(
                email, createdDate);
        return new ResponseEntity<>(salary, HttpStatus.OK);
    }

    /**
     * Update salary base on email and create date
     *
     * @param employeeSalary contains update information
     * @param email          to update salary
     * @param createdDate    to update salary
     * @return message if update success or not
     * @throws ParseException throw parse exception
     */
    @PutMapping(Link.SALARY_EMAIL_CREATED_DATE_PATH_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateSalary(
            @RequestBody EmployeeSalary employeeSalary,
            @PathVariable(name = "email") String email,
            @PathVariable(name = "createdDate") String createdDate)
            throws ParseException {
        String message = salaryServices.processUpdateSalary(
                employeeSalary,
                email,
                createdDate
        );
        HttpStatus status = HttpStatus.OK;

        switch (message) {
            case Message.VALID_CREATED_DATE_END_DATE:
                status = HttpStatus.BAD_REQUEST;
                break;
            case Message.UPDATE_SUCCESS:
                break;
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Delete salary base on email and create date
     *
     * @param email       to delete salary
     * @param createdDate to delete salary
     * @return message if delete success or not
     */
    @DeleteMapping(Link.SALARY_EMAIL_CREATED_DATE_PATH_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteSalary(@PathVariable String email,
                                               @PathVariable String createdDate) {

        String message = salaryServices.processDeleteSalary(
                email, createdDate
        );
        HttpStatus status = HttpStatus.OK;

        switch (message) {
            case Message.DELETE_SALARY_FAIL:
                status = HttpStatus.BAD_REQUEST;
                break;
            case Message.DELETE_SUCCESS:
                break;
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Get all salary history by employee id
     *
     * @param id to get salary history
     * @return salary list base on employee id
     */
    @GetMapping(Link.SALARY_BY_EMPLOYEE_ID_PATH_URL)
    public ResponseEntity<List<EmployeeSalary>> getAllSalariesHistoryByEmployeeId(
            @PathVariable Long id) {
        List<EmployeeSalary> employeeSalaryList =
                salaryServices.findAllSalariesHistoryByEmployeeId(id);
        return new ResponseEntity<>(employeeSalaryList, HttpStatus.OK);
    }
}
