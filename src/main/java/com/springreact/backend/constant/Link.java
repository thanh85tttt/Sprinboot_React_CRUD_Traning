package com.springreact.backend.constant;

/**
 * Link
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
public final class Link {

    /**
     * Common URL
     */
    public final static String REACT_URL = "http://localhost:3000";
    public final static String BASE_URL = "/api/v1";

    /**
     * Auth API
     */
    public final static String SIGN_IN = "/auth/signin";
    public final static String SIGN_UP = "/auth/signup";

    /**
     * Employee API
     */
    public final static String EMPLOYEE_URL = "/employees";
    public final static String EMPLOYEE_ID_PATH_URL = "/employees/{id}";
    public final static String EMPLOYEE_EMAIL_PATH_URL = "/employees/email/{email}";
    public final static String EMPLOYEE_ROLE_DEPARTMENT_URL = "/employees/employees-role-department";
    public final static String EMPLOYEE_ROLE_DEPARTMENT_EMAIL_PATH_URL = "/employees/employees-role-department/{email}";
    public final static String DISTINCT_ADDRESS_URL = "/employees/find-distinct-address";
    public final static String SEARCH_BY_KEYWORD_URL = "/employees/search-employees/{keyword}";
    public final static String LOAD_IMAGE_URL = "/employees/image/{email}";
    public final static String SEARCH_ROLE_DEPARTMENT_BY_KEYWORD_URL = "/employees/search-role-department/{keyword}";

    /**
     * Department API
     */
    public final static String DEPARTMENT_URL = "/departments";
    public final static String DEPARTMENT_ID_PATH_URL = "/departments/{id}";
    public final static String SEARCH_DEPARTMENT_BY_KEYWORD_URL = "/departments/search-departments/{keyword}";

    /**
     * Role API
     */
    public final static String ROLE_URL = "/roles";
    public final static String ROLE_ID_PATH_URL = "/roles/{id}";
    public final static String SEARCH_ROLE_BY_KEYWORD_URL = "/roles/search-roles/{keyword}";

    /**
     * Prefix API
     */
    public final static String PREFIX_URL = "/prefix";
    public final static String PREFIX_TITLE_PATH_URL = "/prefix/{title}";

    /**
     * Salary API
     */
    public final static String SALARY_URL = "/salary";
    public final static String SALARY_ID_PATH_URL = "/salary/{id}";
    public final static String SALARY_EMAIL_PATH_URL = "/salary/{email}";
    public final static String SALARY_BY_EMPLOYEE_EMAIL_PATH_URL = "/salary/employees/{email}";
    public final static String SALARY_EMAIL_CREATED_DATE_PATH_URL =
            "/salary/{email}/{createdDate}";
    public final static String SALARY_EMPLOYEE_EMAIL_CREATED_DATE_PATH_URL =
            "/salary/employee/{email}/{createdDate}";
    public final static String SALARY_BY_EMPLOYEE_ID_PATH_URL = "/salary/employee/{id}";

    /**
     * Password API
     */
    public final static String FORGOT_PASSWORD_EMAIL_PATH_URL = "/forgot_password/{email}";
    public final static String RESET_PASSWORD_TOKEN_PATH_URL = "/reset_password/{token}";
    public final static String CHANGE_PASSWORD_EMAIL_PATH_URL = "/change_password/{email}";
}
