package com.springreact.backend.services;

import com.springreact.backend.constant.Message;
import com.springreact.backend.dto.request.EmployeeRoleAndDepartment;
import com.springreact.backend.entity.Department;
import com.springreact.backend.entity.ERole;
import com.springreact.backend.entity.Employee;
import com.springreact.backend.entity.Role;
import com.springreact.backend.exception.ResourceNotFoundException;
import com.springreact.backend.repository.DepartmentRepository;
import com.springreact.backend.repository.EmployeesRepository;
import com.springreact.backend.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * EmployeeServices
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
@Transactional
public class EmployeeServices {

    private final EmployeesRepository employeesRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * EmployeeServices constructor(EmployeesRepository, RoleRepository, DepartmentRepository)
     *
     * @param employeesRepository  employeesRepository
     * @param roleRepository       roleRepository
     * @param departmentRepository departmentRepository
     */
    @Autowired
    public EmployeeServices(EmployeesRepository employeesRepository,
                            RoleRepository roleRepository,
                            DepartmentRepository departmentRepository) {
        this.employeesRepository = employeesRepository;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
    }

    /**
     * Update reset password token in database
     *
     * @param token generate token to reset password
     * @param email get employee by email
     */
    public void updateResetPasswordToken(String token, String email) {
        Employee employee = employeesRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Message.NOT_EXIST_EMPLOYEE)
                );

        employee.setResetPasswordToken(token);
        employeesRepository.save(employee);
    }

    /**
     * Find employee by token
     *
     * @param token get employee by token
     * @return employee if employee's token exist, otherwise throw exception
     */
    public Employee getByResetPasswordToken(String token) {
        return employeesRepository.findByResetPasswordToken(token)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Message.NOT_EXIST_TOKEN)
                );
    }

    /**
     * Update new password after click the link in email
     *
     * @param employee    employee need to reset password
     * @param newPassword password to reset
     */
    public void updatePassword(Employee employee, String newPassword) {
        //Encode new password, then set it to employee's password and set reset password token to null
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        employee.setPassword(passwordEncoder.encode(newPassword));
        employee.setResetPasswordToken(null);
        employeesRepository.save(employee);
    }

    /**
     * Find employee if employee's email exist, otherwise throw exception
     *
     * @param email get employee by email
     * @return employee by email
     */
    public Employee findEmployeeByEmail(String email) {
        return employeesRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Message.NOT_EXIST_EMPLOYEE + " with email " + email)
                );
    }

    /**
     * Check if old password from request is match with old password in database
     *
     * @param employee    employee need to change password
     * @param oldPassword password to check with old password in database
     */
    public boolean checkValidOldPassword(Employee employee, String oldPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //Check two password are matched or not
        return passwordEncoder.matches(
                oldPassword,
                employee.getPassword()
        );
    }

    /**
     * Change password after all condition is check
     *
     * @param employee need to change password
     * @param password new password
     */
    public void changeUserPassword(Employee employee, String password) {
        //Encode new password and save it
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        employee.setPassword(passwordEncoder.encode(password));
        employeesRepository.save(employee);
    }

    /**
     * Find Employee List by descendant
     */
    public List<Employee> findAllEmployeeOrderByIdDesc() {
        return employeesRepository.findAllByOrderByIdDesc();
    }

    /**
     * Create new employee
     */
    public String processCreateEmployee(Employee employee) {
        String message = null;
        boolean isEmployeeByEmailExist = employeesRepository
                .existsEmployeeByEmail(employee.getEmail().trim());
        boolean isEmployeeByPhoneExist = employeesRepository
                .existsEmployeeByPhone(employee.getPhone().trim());

        //Check if email and phone is already in use
        if (!isEmployeeByEmailExist && !isEmployeeByPhoneExist) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            employee.setFullName(employee.getFullName().trim());
            employee.setAddress(employee.getAddress().trim());
            employee.setEmail(employee.getEmail().trim());
            employee.setPhone(employee.getPhone().trim());
            Role userRole = roleRepository.findByRole(ERole.ROLE_USER.name())
                    .orElseThrow(() -> new RuntimeException(Message.NOT_EXIST_ROLE));
            employee.setRole(userRole);
            employee.setActive(true);
            employeesRepository.save(employee);
            message = Message.CREATE_SUCCESS;
        } else if (isEmployeeByEmailExist) {
            message = Message.EXIST_EMAIL;
        } else {
            message = Message.EXIST_PHONE_NUMBER;
        }

        return message;
    }

    /**
     * Find an employee by id
     */
    public Employee findEmployeeById(Long id) {
        return employeesRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                Message.NOT_EXIST_EMPLOYEE + " with id " + id)
                );
    }

    /**
     * Update employee information
     *
     * @param employeeUpdate get new employee information and pass it to old employee
     */
    public String processUpdateEmployee(Employee employeeUpdate, Long id) {
        boolean isEmployeeByEmailExist = employeesRepository
                .existsEmployeeByEmail(employeeUpdate.getEmail());
        boolean isEmployeeByPhoneExist = employeesRepository
                .existsEmployeeByPhone(employeeUpdate.getPhone());
        String message = null;

        Employee employee = employeesRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                Message.NOT_EXIST_EMPLOYEE + " with id " + id)
                );

        //Check email exist except its old email
        if (employee.getEmail() != null && !employee.getEmail().equals(employeeUpdate.getEmail())) {
            if (isEmployeeByEmailExist) {
                message = Message.EXIST_EMAIL;
                return message;
            }

            //If old phone number null and new phone number exist in database
        } else if (employee.getPhone() == null && isEmployeeByPhoneExist) {
            message = Message.EXIST_PHONE_NUMBER;
            return message;

            //Check phone exist except its old phone
        } else if (employee.getPhone() != null && !employee.getPhone().equals(employeeUpdate.getPhone())) {
            if (isEmployeeByPhoneExist) {
                message = Message.EXIST_PHONE_NUMBER;
                return message;
            }
        }

        employee.setFullName(employeeUpdate.getFullName().trim());
        employee.setAddress(employeeUpdate.getAddress().trim());
        employee.setEmail(employeeUpdate.getEmail().trim());
        employee.setPhone(employeeUpdate.getPhone().trim());
        employee.setGender(employeeUpdate.getGender());
        employee.setImage(employeeUpdate.getImage());

        employeesRepository.save(employee);
        message = Message.UPDATE_SUCCESS;
        return message;
    }

    /**
     * Delete employee by employee id
     *
     * @param id to delete employee
     * @return message if delete success or not
     */
    public String processDeleteEmployee(Long id) {
        Employee employee = employeesRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                Message.NOT_EXIST_EMPLOYEE + " with id " + id)
                );
        employee.setActive(false);
        employeesRepository.save(employee);
        return Message.DELETE_SUCCESS;
    }

    /**
     * Get role and department of all employees
     */
    public List<EmployeeRoleAndDepartment> listEmployeeRoleAndDepartment() {
        List<Employee> employeeList = employeesRepository.findAllByOrderByIdDesc();
        List<EmployeeRoleAndDepartment> employeeRoleAndDepartmentList = new ArrayList<>();
        EmployeeRoleAndDepartment employeeRoleAndDepartment = null;

        //Set role and department from employeeList to employeeRoleAndDepartmentList
        try {
            for (Employee employee : employeeList) {
                employeeRoleAndDepartment = new EmployeeRoleAndDepartment();
                //If role exist, set role to dto
                if (employee.getRole() != null) {

                    employeeRoleAndDepartment.setRole(employee.getRole().getRole());
                }
                //if department exist, set department to dto
                if (employee.getDepartment() != null) {
                    employeeRoleAndDepartment.setDepartment(employee.getDepartment().getDepartment());
                }
                employeeRoleAndDepartment.setEmail(employee.getEmail());
                employeeRoleAndDepartment.setFullName(employee.getFullName());
                employeeRoleAndDepartmentList.add(employeeRoleAndDepartment);
            }
        } catch (NullPointerException e) {
            logger.error("An error occur: " + e.getMessage());
            throw new NullPointerException(Message.NOT_EXIST_EMPLOYEE_LIST);
        }

        return employeeRoleAndDepartmentList;
    }

    /**
     * Update role and department of an employee
     *
     * @param employeeRoleAndDepartmentUpdate contains update information
     * @param email                           to update role and department
     * @return message if update success or not
     */
    public String processUpdateEmployeeRoleAndDepartment(
            EmployeeRoleAndDepartment employeeRoleAndDepartmentUpdate,
            String email) {

        String message = null;
        Employee employee = employeesRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                Message.NOT_EXIST_EMPLOYEE + " with email " + email)
                );
        boolean isRoleExist = roleRepository
                .existsByRole(employeeRoleAndDepartmentUpdate.getRole());
        boolean isDepartmentExist = departmentRepository
                .existsByDepartment(employeeRoleAndDepartmentUpdate.getDepartment());

        if (!isRoleExist) {
            message = Message.NOT_EXIST_ROLE;
            return message;
        }

        if (!isDepartmentExist) {
            message = Message.NOT_EXIST_DEPARTMENT;
            return message;
        }

        Optional<Role> role = roleRepository
                .findByRole(employeeRoleAndDepartmentUpdate.getRole());
        Optional<Department> department = departmentRepository
                .findByDepartment(employeeRoleAndDepartmentUpdate.getDepartment());

        role.ifPresent(employee::setRole);
        department.ifPresent(employee::setDepartment);

        employeesRepository.save(employee);
        message = Message.UPDATE_ROLE_DEPARTMENT_SUCCESS;
        return message;
    }

    /**
     * Find role and department of and employee by employee email
     *
     * @param email to find role and department
     * @return role and department of an employee
     */
    public EmployeeRoleAndDepartment findEmployeeRoleAndDepartmentByEmail(String email) {
        Employee employee = employeesRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                Message.NOT_EXIST_EMPLOYEE + " with email " + email)
                );
        EmployeeRoleAndDepartment employeeRoleAndDepartment = null;

        try {
            employeeRoleAndDepartment = new EmployeeRoleAndDepartment();
            employeeRoleAndDepartment.setEmail(employee.getEmail());
            employeeRoleAndDepartment.setFullName(employee.getFullName());
            if (employee.getRole() != null) {
                employeeRoleAndDepartment.setRole(employee.getRole().getRole());
            }
            if (employee.getDepartment() != null) {
                employeeRoleAndDepartment.setDepartment(employee.getDepartment().getDepartment());
            }
        } catch (NullPointerException e) {
            logger.error("An error occur: " + e.getMessage());
            throw new NullPointerException(Message.NOT_EXIST_ROLE_DEPARTMENT_LIST);
        }

        return employeeRoleAndDepartment;
    }

    /**
     * Search employees by keyword and pass them to front end
     *
     * @param keyword to search employee list
     * @return employee list base on keyword
     */
    public List<Employee> processSearchEmployees(String keyword) {
        List<Employee> employeeList = null;

        try {
            if (keyword != null) {
                employeeList = employeesRepository.findEmployees(keyword);
            } else {
                employeeList = employeesRepository.findAll();
            }
        } catch (NullPointerException e) {
            logger.error("An error occur: " + e.getMessage());
            throw new NullPointerException(Message.NOT_EXIST_EMPLOYEE_LIST);
        } catch (NumberFormatException e) {
            logger.error("An error occur: " + e.getMessage());
            throw new NumberFormatException(Message.NOT_EXIST_EMPLOYEE_LIST);
        }

        return employeeList;
    }

    /**
     * Search employees by keyword and pass them to front end
     *
     * @param keyword to search employee list
     * @return employee list base on keyword
     */
    public List<EmployeeRoleAndDepartment> processSearchEmployeeRoleAndDepartment(String keyword) {
        List<Employee> employeeList = employeesRepository.findEmployeesByFullNameAndEmail(keyword);
        List<EmployeeRoleAndDepartment> employeeRoleAndDepartmentList = new ArrayList<>();
        EmployeeRoleAndDepartment employeeRoleAndDepartment = null;

        //Set role and department from employeeList to employeeRoleAndDepartmentList
        try {
            for (Employee employee : employeeList) {
                employeeRoleAndDepartment = new EmployeeRoleAndDepartment();
                //If role exist, set role to dto
                if (employee.getRole() != null) {
                    employeeRoleAndDepartment.setRole(employee.getRole().getRole());
                }
                //if department exist, set department to dto
                if (employee.getDepartment() != null) {
                    employeeRoleAndDepartment.setDepartment(employee.getDepartment().getDepartment());
                }
                employeeRoleAndDepartment.setEmail(employee.getEmail());
                employeeRoleAndDepartment.setFullName(employee.getFullName());
                employeeRoleAndDepartmentList.add(employeeRoleAndDepartment);
            }
        } catch (NullPointerException e) {
            logger.error("An error occur: " + e.getMessage());
            throw new NullPointerException(Message.NOT_EXIST_EMPLOYEE_LIST);
        }

        return employeeRoleAndDepartmentList;
    }

    /**
     * Find employee's image by employee's email to make an avatar
     *
     * @param email to find employee's image
     * @return image path
     */
    public String findEmployeeImageByEmail(String email) {
        Employee employee = employeesRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                Message.NOT_EXIST_EMAIL + " with email " + email
                        )
                );
        return employee.getImage();
    }

    /**
     * Get distinct addresses to filter on employee table
     *
     * @return distinct address list
     */
    public List<String> findDistinctAddress() {
        List<String> addressList = null;

        try {
            addressList = employeesRepository.findDistinctAddress();
        } catch (NullPointerException e) {
            logger.error("An error occur: " + e.getMessage());
            throw new NullPointerException(Message.NOT_EXIST_ADDRESS_LIST);
        }

        return addressList;
    }

}











