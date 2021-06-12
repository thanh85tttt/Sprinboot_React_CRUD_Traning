package com.springreact.backend.repository;

import com.springreact.backend.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * SalaryRepository
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
public interface SalaryRepository extends JpaRepository<Salary, Long> {

    /**
     * Find salaries by full name of employee
     *
     * @param fullName employee full name
     * @return salary list
     */
    List<Salary> findByEmployee_FullName(String fullName);

    /**
     * Find salaries by email of employee
     *
     * @param email employee email
     * @return salary list
     */
    List<Salary> findByEmployee_Email(String email);

    /**
     * Find all salaries by active is true order by id
     *
     * @param active salary is available or not
     * @return salary list
     */
    List<Salary> findAllByActiveEqualsOrderById(Boolean active);

    /**
     * Check exist by employee email and issue date of salary
     *
     * @param email       to check exist
     * @param createdDate to check exist
     * @return true if exist and false otherwise
     */
    Boolean existsByEmployee_EmailAndCreatedDate(String email, String createdDate);

    /**
     * Find salary by employee email and create date
     *
     * @param email       to find salary
     * @param createdDate to find salary
     * @return salary by employee email and create date
     */
    Salary findByEmployee_EmailAndCreatedDate(String email, String createdDate);
}
