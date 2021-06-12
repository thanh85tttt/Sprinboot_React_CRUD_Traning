package com.springreact.backend.constant;

/**
 * Message
 *
 * Version 1.0
 *
 * Date: 05-06-2021
 *
 * Copyright By Thanh
 *
 * Modification Logs:
 * DATE             AUTHOR              DESCRIPTION
 * -------------------------------------------------
 * 07-06-2021       ThanhBT11           Create
 */
public final class Message {

    /**
     * Auth message
     */
    public final static String USERNAME_HAS_TAKEN = "Username is already taken!";
    public final static String REGISTER_SUCCESS = "User registered successfully!";
    public final static String VALID_CONFIRM_PASSWORD = "Confirm password must equal to password!";
    public final static String CHANGE_PASSWORD_SUCCESS = "Change password successfully!";
    public final static String CHANGE_PASSWORD_FAIL = "Change password failed!";
    public final static String INCORRECT_OLD_PASSWORD = "Old password is incorrect!";

    /**
     * CRUD message
     */
    public final static String CREATE_SUCCESS = "Create Successfully!";
    public final static String UPDATE_SUCCESS = "Update Successfully!";
    public final static String DELETE_SUCCESS = "Delete Successfully!";

    /**
     * Mail message
     */
    public final static String SENT_EMAIL = "We have sent a reset password link to your email. Please check!";
    public final static String EXIST_EMAIL = "Email is already exist!";
    public final static String NOT_EXIST_EMAIL = "Email no longer exist";

    /**
     * Employee message
     */
    public final static String NOT_EXIST_EMPLOYEE = "Employee no longer exist";
    public final static String EXIST_EMPLOYEE_BY_ROLE =
            "There are some employee has this role. " +
            "You can only delete when no one keeping it!";
    public final static String EXIST_EMPLOYEE_BY_DEPARTMENT =
            "There are some employee in this department. " +
            "You can only delete when no one keeping it!";
    public final static String NOT_EXIST_EMPLOYEE_LIST = "Employee list is empty!";

    /**
     * Role message
     */
    public final static String EXIST_ROLE = "Role is already exist!";
    public final static String NOT_EXIST_ROLE = "Role no longer exist";
    public final static String NOT_EXIST_ROLE_LIST = "Role list is empty!";

    /**
     * Department message
     */
    public final static String EXIST_DEPARTMENT = "Department is already exist!";
    public final static String EXIST_DEPARTMENT_NAME = "Department name is already exist!";
    public final static String NOT_EXIST_DEPARTMENT = "Department no longer exist";
    public final static String NOT_EXIST_DEPARTMENT_LIST = "Department list is empty!";

    /**
     * Other message
     */
    public final static String UPDATE_ROLE_DEPARTMENT_SUCCESS = "Update Role and Department successfully!";
    public final static String VALID_CREATED_DATE_END_DATE = "End Date must greater than created Date";
    public final static String EXIST_PHONE_NUMBER = "Phone number is already exist!";
    public final static String NOT_EXIST_SALARY = "Salary no longer exist";
    public final static String NOT_EXIST_TOKEN = "Token no longer exist";
    public final static String NOT_EXIST_PREFIX_LIST = "Prefix list is empty!";
    public final static String NOT_EXIST_ROLE_DEPARTMENT_LIST = "Role and department is empty!";
    public final static String NOT_EXIST_ADDRESS_LIST = "Address list is empty!";
    public final static String PARSE_DATE_ERROR = "There are some errors when process parse date!";

    /**
     * Salary message
     */
    public final static String DELETE_SALARY_FAIL = "Salary is already inactive!";
    public final static String NOT_EXIST_SALARY_LIST = "Salary list is empty!";

}
