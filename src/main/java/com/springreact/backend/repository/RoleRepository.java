package com.springreact.backend.repository;

import com.springreact.backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * RoleRepository
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
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find role by role name
     *
     * @param role role name
     * @return role optional
     */
    Optional<Role> findByRole(String role);

    /**
     * Check exist role by role name
     *
     * @param role role name
     * @return true if exist and false otherwise
     */
    Boolean existsByRole(String role);

    /**
     * Find all roles order by role name (Ascending)
     *
     * @return role list
     */
    List<Role> findAllByOrderByRoleAsc();

    /**
     * Find roles by keyword
     *
     * @param keyword to find roles
     * @return role list
     */
    @Query("SELECT r FROM Role r WHERE CONCAT(r.role, r.createdDate) LIKE %?1%  ")
    List<Role> findRolesByKeyword(String keyword);
}













