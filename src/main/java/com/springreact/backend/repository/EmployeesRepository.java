package com.springreact.backend.repository;

import com.springreact.backend.entity.Department;
import com.springreact.backend.entity.Employee;
import com.springreact.backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * EmployeesRepository
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
@Repository
public interface EmployeesRepository extends JpaRepository<Employee, Long> {

    /**
     * Find employee by email
     *
     * @param email to find employee
     * @return employee by email
     */
    Employee findEmployeeByEmail(String email);

    /**
     * Find employee by phone
     *
     * @param phone to find employee
     * @return employee by phone
     */
    Employee findEmployeeByPhone(String phone);

    /**
     * Find all employee order by id (Descending)
     *
     * @return list of employees
     */
    List<Employee> findAllByOrderByIdDesc();

    /**
     * Find distinct addresses
     *
     * @return list of distinct addresses
     */
    @Query("select distinct address from Employee")
    List<String> findDistinctAddress();

    /**
     * Search employees by keyword
     *
     * @param keyword to search employee
     * @return list employees by keyword
     */
    @Query("SELECT e FROM Employee e WHERE CONCAT(e.fullName, e.address, e.email, e.gender, e.phone) LIKE %?1%  ")
    List<Employee> findEmployees(String keyword);

    /**
     * Find by email
     *
     * @param email to find employee
     * @return employee optional
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Check exist employee by email
     *
     * @param email to check exist
     * @return true if exist or false if not
     */
    Boolean existsByEmail(String email);

    /**
     * Find employee by id
     *
     * @param aLong is id
     * @return employee optional
     */
    @Override
    Optional<Employee> findById(Long aLong);

    /**
     * Find employee by reset password token
     *
     * @param token to find employee
     * @return employee optional
     */
    Optional<Employee> findByResetPasswordToken(String token);

    /**
     * Check employee exist by role
     *
     * @param role employee role
     * @return true if exist or false if not
     */
    Boolean existsByRole(Role role);

    /**
     * Check employee exist by department
     *
     * @param department employee department
     * @return true if exist or false if not
     */
    Boolean existsByDepartment(Department department);

    /**
     * Check employee exist by email
     *
     * @param email to check exist
     * @return true if exist or false if not
     */
    Boolean existsEmployeeByEmail(String email);

    /**
     * Check employee exist by phone
     *
     * @param phone to check exist
     * @return true if exist or false if not
     */
    Boolean existsEmployeeByPhone(String phone);

    /**
     * Find employee by full name and email
     *
     * @param keyword to find employee
     * @return employee list by full name and email
     */
    @Query("SELECT e FROM Employee e WHERE CONCAT(e.fullName, e.email) LIKE %?1%  ")
    List<Employee> findEmployeesByFullNameAndEmail(String keyword);
}









