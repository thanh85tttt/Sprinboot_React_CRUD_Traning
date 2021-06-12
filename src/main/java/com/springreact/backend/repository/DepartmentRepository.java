package com.springreact.backend.repository;

import com.springreact.backend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * DepartmentRepository
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
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    /**
     * Find by id repository
     *
     * @param aLong is id
     * @return department optional
     */
    @Override
    Optional<Department> findById(Long aLong);

    /**
     * Find by department
     *
     * @param department department code
     * @return department optional
     */
    Optional<Department> findByDepartment(String department);

    /**
     * Check exist department
     *
     * @param department department code
     * @return true if exist or false if not
     */
    Boolean existsByDepartment(String department);

    /**
     * Check exist department by department name
     *
     * @param fullName department name
     * @return true if exist or false if not
     */
    Boolean existsByFullName(String fullName);

    /**
     * Find all departments order by department code (Ascending)
     *
     * @return list of departments
     */
    List<Department> findAllByOrderByDepartmentAsc();

    /**
     * Find departments by keyword
     *
     * @param keyword to find departments
     * @return list of departments
     */
    @Query("SELECT d FROM Department d WHERE CONCAT(d.department, d.createdDate, d.fullName) LIKE %?1%  ")
    List<Department> findDepartmentsByKeyword(String keyword);

}








