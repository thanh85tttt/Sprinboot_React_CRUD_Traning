package com.springreact.backend.services;

import com.springreact.backend.constant.Message;
import com.springreact.backend.constant.Pattern;
import com.springreact.backend.dto.request.DepartmentName;
import com.springreact.backend.entity.Department;
import com.springreact.backend.exception.ResourceNotFoundException;
import com.springreact.backend.repository.DepartmentRepository;
import com.springreact.backend.repository.EmployeesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * DepartmentServices
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
public class DepartmentServices {

    private final DepartmentRepository departmentRepository;
    private final EmployeesRepository employeesRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * DepartmentServices constructor(DepartmentRepository, EmployeesRepository)
     *
     * @param departmentRepository departmentRepository
     * @param employeesRepository  employeesRepository
     */
    public DepartmentServices(DepartmentRepository departmentRepository,
                              EmployeesRepository employeesRepository) {
        this.departmentRepository = departmentRepository;
        this.employeesRepository = employeesRepository;
    }

    /**
     * Find all department order by id desc
     */
    public List<Department> findAllByOrderByDepartmentAsc() {
        return departmentRepository.findAllByOrderByDepartmentAsc();
    }

    /**
     * Create new department
     *
     * @param departmentName contains create information
     * @return message if success or not
     */
    public String processCreateDepartment(DepartmentName departmentName) {
        String message = null;
        boolean isDepartmentExist = departmentRepository
                .existsByDepartment(departmentName.getDepartment());
        boolean isDepartmentNameExist = departmentRepository
                .existsByFullName(departmentName.getFullName());

        //Check if already had department by department
        if (isDepartmentExist) {
            message = Message.EXIST_DEPARTMENT;
            return message;
        }

        //Check if already had department by department name
        if (isDepartmentNameExist) {
            message = Message.EXIST_DEPARTMENT_NAME;
            return message;
        }

        Department department = new Department();
        department.setDepartment(departmentName.getDepartment().toUpperCase().trim());
        department.setFullName(departmentName.getFullName().trim());
        department.setActive(true);

        DateFormat dateFormat = new SimpleDateFormat(Pattern.DATE_PATTERN);
        department.setCreatedDate(dateFormat.format(Calendar.getInstance().getTime()));

        departmentRepository.save(department);
        message = Message.CREATE_SUCCESS;
        return message;
    }

    /**
     * Delete department by department id
     *
     * @param id to delete department
     * @return message if success or not
     */
    public String processDeleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Message.NOT_EXIST_DEPARTMENT + " with id " + id)
                );

        department.setActive(false);
        departmentRepository.save(department);
        return Message.DELETE_SUCCESS;
    }

    /**
     * Find department by department id
     *
     * @param id to find department
     * @return department by department id
     */
    public Department findDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Message.NOT_EXIST_DEPARTMENT + " with id " + id)
                );
    }

    /**
     * Update department by department id
     *
     * @param departmentUpdate contains update information
     * @param id               to update department
     * @return message if update success or not
     */
    public String processUpdateDepartment(Department departmentUpdate, Long id) {
        String message = null;

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Message.NOT_EXIST_DEPARTMENT + " with id " + id)
                );

        //Check exist department except its department
        if (!department.getDepartment().equalsIgnoreCase(departmentUpdate.getDepartment()) &&
                departmentRepository.existsByDepartment(departmentUpdate.getDepartment())) {
            message = Message.EXIST_DEPARTMENT;
            return message;
        }

        //Check exist department except its department name
        if (!department.getFullName().equalsIgnoreCase(departmentUpdate.getFullName()) &&
                departmentRepository.existsByFullName(departmentUpdate.getFullName())) {
            message = Message.EXIST_DEPARTMENT_NAME;
            return message;
        }

        department.setDepartment(departmentUpdate.getDepartment().toUpperCase().trim());
        department.setCreatedDate(departmentUpdate.getCreatedDate());
        department.setFullName(departmentUpdate.getFullName().trim());

        departmentRepository.save(department);

        message = Message.UPDATE_SUCCESS;
        return message;
    }

    /**
     * Check exist employee in department
     *
     * @param id to check exist
     * @return true if exist, false if not
     */
    public Boolean isEmployeeExistByDepartment(Long id) {
        Department department = findDepartmentById(id);
        return employeesRepository.existsByDepartment(department);
    }

    /**
     * Search employees by keyword and pass them to front end
     *
     * @param keyword to search employee list
     * @return employee list base on keyword
     */
    public List<Department> processSearchDepartments(String keyword) {
        List<Department> departmentList = null;

        try {
            //If keyword is exist, find departments by keyword, otherwise find all departments
            if (keyword != null) {
                departmentList = departmentRepository.findDepartmentsByKeyword(keyword);
            } else {
                departmentList = departmentRepository.findAll();
            }
        } catch (NullPointerException e) {
            logger.error("An error occur: " + e.getMessage());
            throw new NullPointerException(Message.NOT_EXIST_DEPARTMENT_LIST);
        } catch (NumberFormatException e) {
            logger.error("An error occur: " + e.getMessage());
            throw new NumberFormatException(Message.NOT_EXIST_DEPARTMENT_LIST);
        }

        return departmentList;
    }
}
