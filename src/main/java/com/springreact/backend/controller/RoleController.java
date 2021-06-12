package com.springreact.backend.controller;

import com.springreact.backend.constant.Link;
import com.springreact.backend.constant.Message;
import com.springreact.backend.dto.request.RoleName;
import com.springreact.backend.entity.Role;
import com.springreact.backend.exception.ResourceNotFoundException;
import com.springreact.backend.services.RoleServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RoleController
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
public class RoleController {

    private final RoleServices roleServices;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * RoleController constructor(RoleServices)
     *
     * @param roleServices roleServices
     */
    public RoleController(RoleServices roleServices) {
        this.roleServices = roleServices;
    }

    /**
     * Get All Roles
     *
     * @return role list
     */
    @GetMapping(Link.ROLE_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = null;

        try {
            roles = roleServices.findAllByOrderByRoleAsc();
        } catch (NullPointerException e) {
            logger.error("An error occur: " + e.getMessage());
            throw new ResourceNotFoundException(Message.NOT_EXIST_ROLE_LIST);
        }

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    /**
     * Create new role
     *
     * @param roleName contain role name
     * @return message if create success or not
     */
    @PostMapping(Link.ROLE_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createRole(@RequestBody RoleName roleName) {
        String message = roleServices.processCreateRole(roleName);
        HttpStatus status = HttpStatus.OK;

        switch (message) {
            case Message.EXIST_ROLE:
                status = HttpStatus.BAD_REQUEST;
                break;
            case Message.CREATE_SUCCESS:
                break;
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Delete role by role id
     *
     * @param id to check is there any employees exist and process delete role
     * @return message if delete success or not
     */
    @DeleteMapping(Link.ROLE_ID_PATH_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        Boolean isEmployeeExistByRole = roleServices.isEmployeeExistByRole(id);
        String message = null;
        HttpStatus status = HttpStatus.OK;

        if (isEmployeeExistByRole) {
            message = Message.EXIST_EMPLOYEE_BY_ROLE;
            status = HttpStatus.BAD_REQUEST;
        } else {
            message = roleServices.processDeleteRole(id);
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Get role by role id
     *
     * @param id to get role
     * @return role by role id
     */
    @GetMapping(Link.ROLE_ID_PATH_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Role role = roleServices.findRoleById(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    /**
     * Update role by role id
     *
     * @param roleUpdate contains update information
     * @param id         to find and update role
     * @return message if update success or not
     */
    @PutMapping(Link.ROLE_ID_PATH_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateRole(@RequestBody Role roleUpdate,
                                             @PathVariable(name = "id") Long id) {

        String message = roleServices.processUpdateRole(roleUpdate, id);
        HttpStatus status = HttpStatus.OK;

        switch (message) {
            case Message.EXIST_ROLE:
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
    @GetMapping(Link.SEARCH_ROLE_BY_KEYWORD_URL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Role>> searchRoles(@PathVariable String keyword) {

        List<Role> roleList = roleServices.processSearchRoles(keyword);

        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }
}
