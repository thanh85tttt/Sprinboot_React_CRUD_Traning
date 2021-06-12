package com.springreact.backend.services;

import com.springreact.backend.constant.Message;
import com.springreact.backend.constant.Pattern;
import com.springreact.backend.dto.request.RoleName;
import com.springreact.backend.entity.Role;
import com.springreact.backend.exception.ResourceNotFoundException;
import com.springreact.backend.repository.EmployeesRepository;
import com.springreact.backend.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * RoleServices
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
public class RoleServices {

    private final RoleRepository roleRepository;

    private final EmployeesRepository employeesRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * RoleServices constructor
     *
     * @param roleRepository      roleRepository
     * @param employeesRepository employeesRepository
     */
    @Autowired
    public RoleServices(RoleRepository roleRepository,
                        EmployeesRepository employeesRepository) {
        this.roleRepository = roleRepository;
        this.employeesRepository = employeesRepository;
    }

    /**
     * Find all roles order by id descendant
     */
    public List<Role> findAllByOrderByRoleAsc() {
        return roleRepository.findAllByOrderByRoleAsc();
    }

    /**
     * Create new role
     *
     * @param roleName contains create information
     * @return message if create success or not
     */
    public String processCreateRole(RoleName roleName) {
        boolean isRoleExist = roleRepository.existsByRole(roleName.getRole());
        String message = null;

        if (isRoleExist) {
            message = Message.EXIST_ROLE;
            return message;
        }

        Role role = new Role();
        role.setRole(roleName.getRole().toUpperCase());
        role.setActive(true);

        //Create date is current date
        DateFormat dateFormat = new SimpleDateFormat(Pattern.DATE_PATTERN);
        role.setCreatedDate(dateFormat.format(Calendar.getInstance().getTime()));

        roleRepository.save(role);
        message = Message.CREATE_SUCCESS;
        return message;
    }

    /**
     * Delete role by role id
     *
     * @param id to delete role
     * @return message if delete success or not
     */
    public String processDeleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                Message.NOT_EXIST_ROLE + " with id " + id
                        )
                );

        role.setActive(false);
        roleRepository.save(role);
        return Message.DELETE_SUCCESS;
    }

    /**
     * Check exist employee by role
     *
     * @param id to check exist
     * @return true if exist, false if not
     */
    public Boolean isEmployeeExistByRole(Long id) {
        Role role = findRoleById(id);
        return employeesRepository.existsByRole(role);
    }

    /**
     * Find role by role id
     *
     * @param id to find role
     * @return role if exist
     */
    public Role findRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                Message.NOT_EXIST_ROLE + " with id " + id)
                );
    }

    /**
     * Update role
     *
     * @param roleUpdate contains update information
     * @param id         to update role
     * @return message if update success or not
     */
    public String processUpdateRole(Role roleUpdate, Long id) {
        boolean isExistByRole = roleRepository.existsByRole(roleUpdate.getRole());
        String message = null;

        Role role = roleRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                Message.NOT_EXIST_ROLE + " with id " + id)
                );

        //Check if role is exist or not
        if (!role.getRole().equalsIgnoreCase(roleUpdate.getRole())) {
            if (isExistByRole) {
                message = Message.EXIST_ROLE;
                return message;
            }
        }

        role.setRole(roleUpdate.getRole().toUpperCase());
        role.setCreatedDate(roleUpdate.getCreatedDate());
        roleRepository.save(role);

        message = Message.UPDATE_SUCCESS;
        return message;
    }

    /**
     * Search employees by keyword and pass them to front end
     *
     * @param keyword to search employee list
     * @return employee list base on keyword
     */
    public List<Role> processSearchRoles(String keyword) {
        List<Role> roleList = null;

        try {
            if (keyword != null) {
                roleList = roleRepository.findRolesByKeyword(keyword);
            } else {
                roleList = roleRepository.findAll();
            }
        } catch (NumberFormatException e) {
            logger.error("An error occur: " + e.getMessage());
            throw new ResourceNotFoundException(Message.NOT_EXIST_DEPARTMENT_LIST);
        } catch (NullPointerException e) {
            logger.error("An error occur: " + e.getMessage());
            throw new NullPointerException(Message.NOT_EXIST_DEPARTMENT_LIST);
        }

        return roleList;
    }
}











