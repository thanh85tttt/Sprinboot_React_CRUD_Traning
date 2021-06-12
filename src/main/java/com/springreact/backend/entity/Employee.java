package com.springreact.backend.entity;

import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Employee
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
@Entity
@Table(name = "employees",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        }
)
@Where(clause = "active != 0")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "gender")
    private String gender;

    @Column(name = "image")
    private String image;

    @Column(name = "active")
    private boolean active;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId", referencedColumnName = "id")
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departmentId", referencedColumnName = "id")
    private Department department;

    /**
     * Default constructor
     */
    public Employee() {
    }

    /**
     * Employee constructor(String, String)
     *
     * @param email    email of employee
     * @param password password to authentication
     */
    public Employee(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Employee constructor(String, String, String, String, String)
     *
     * @param fullName employee full name
     * @param email    employee email
     * @param phone    employee phone number
     * @param address  employee address
     * @param gender   employee gender
     * @param image    avatar of employee
     */
    public Employee(String fullName,
                    String email,
                    String phone,
                    String address,
                    String gender,
                    String image) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.image = image;
    }

    /**
     * Employee constructor(String, String, String, String, String, String, String)
     *
     * @param fullName employee full name
     * @param email    employee email
     * @param phone    employee phone number
     * @param address  employee address
     * @param password account password
     * @param gender   employee gender
     * @param image    employee avatar
     */
    public Employee(String fullName,
                    String email,
                    String phone,
                    String address,
                    String password,
                    String gender,
                    String image) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.gender = gender;
        this.image = image;
    }

    /**
     * Get id
     *
     * @return employee id
     */
    public long getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id employee id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get full name
     *
     * @return employee full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Set full name
     *
     * @param fullName employee full name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Get employee
     *
     * @return employee email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email
     *
     * @param email employee email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get phone number
     *
     * @return phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set phone number
     *
     * @param phone employee phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Get address
     *
     * @return employee address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set address
     *
     * @param address employee address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get gender
     *
     * @return employee gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Set gender
     *
     * @param gender employee gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Get image
     *
     * @return employee avatar
     */
    public String getImage() {
        return image;
    }

    /**
     * Set image
     *
     * @param image employee avatar
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Get password
     *
     * @return account password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password
     *
     * @param password account password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get role
     *
     * @return employee role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Set role
     *
     * @param role employee role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Get department
     *
     * @return employee department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Set department
     *
     * @param department employee department
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * Check employee is active or not
     *
     * @return active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set active
     *
     * @param active employee is available or not
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Get reset password token
     *
     * @return reset password token
     */
    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    /**
     * Set reset password token
     *
     * @param resetPasswordToken token to check when employee send mail forgot password
     */
    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    /**
     * Get all information of employee
     *
     * @return employee information
     */
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", image='" + image + '\'' +
                ", active=" + active +
                ", role=" + role +
                ", department=" + department +
                '}';
    }
}











