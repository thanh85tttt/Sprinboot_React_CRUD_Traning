package com.springreact.backend.services;

import com.springreact.backend.constant.Message;
import com.springreact.backend.constant.Pattern;
import com.springreact.backend.dto.request.EmployeeSalary;
import com.springreact.backend.entity.Employee;
import com.springreact.backend.entity.Salary;
import com.springreact.backend.exception.ParseDateException;
import com.springreact.backend.exception.ResourceNotFoundException;
import com.springreact.backend.repository.EmployeesRepository;
import com.springreact.backend.repository.SalaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SalaryServices
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
public class SalaryServices {

    private final EmployeesRepository employeesRepository;

    private final SalaryRepository salaryRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * SalaryServices constructor(EmployeesRepository, SalaryRepository)
     *
     * @param employeesRepository
     * @param salaryRepository
     */
    public SalaryServices(EmployeesRepository employeesRepository,
                          SalaryRepository salaryRepository) {
        this.employeesRepository = employeesRepository;
        this.salaryRepository = salaryRepository;
    }

    /**
     * Find Latest salaries, then order them by descendant
     * EmployeeSalary Dto use to display useful information to frontend
     *
     * @return EmployeeSalary list
     * @throws ParseException throw ParseException
     */
    public List<EmployeeSalary> findAllSalaryOrderByIdDesc() throws ParseException {
        List<Salary> salaryList = findLatestSalaryOfEachEmployees();
        List<EmployeeSalary> employeeSalaryList = new ArrayList<>();
        EmployeeSalary employeeSalary = null;

        try {
            //Convert Salary from SalaryList to EmployeeSalary from EmployeeSalary list
            for (Salary salary : salaryList) {
                employeeSalary = new EmployeeSalary();

                //If create date exist, set it to create date of employeeSalary
                if (salary.getCreatedDate() != null) {
                    employeeSalary.setCreatedDate(salary.getCreatedDate());
                }

                //If end date exist, set it to end date of employeeSalary
                if (salary.getEndDate() != null) {
                    employeeSalary.setEndDate(salary.getEndDate());
                }

                //If both create date and end date exist, set them to end date of employeeSalary
                if (salary.getCreatedDate() != null || salary.getEndDate() != null) {
                    employeeSalary.setCreatedDate(salary.getCreatedDate());
                    employeeSalary.setEndDate(salary.getEndDate());
                }
                employeeSalary.setEmployee(salary.getEmployee().getFullName());
                employeeSalary.setEmail(salary.getEmployee().getEmail());
                employeeSalary.setSalary(salary.getSalary());
                employeeSalaryList.add(employeeSalary);
            }
        } catch (NullPointerException e) {
            logger.error("An error occurred: " + e.getMessage());
            throw new NullPointerException(Message.NOT_EXIST_SALARY);
        }

        return employeeSalaryList;
    }

    /**
     * Find current salary that actively in database
     *
     * @return latest salary list of each employee
     * @throws ParseException throw ParseException
     */
    public List<Salary> findLatestSalaryOfEachEmployees() throws ParseException {
        List<Salary> salaryList = salaryRepository.findAllByActiveEqualsOrderById(true);
        List<Salary> newSalaryList = new ArrayList<>();
        Date dateBefore;
        Date dateAfter;
        boolean isSalaryExist;
        int newSalaryListSize = 0;

        try {
            for (Salary salary : salaryList) {
                newSalaryListSize = newSalaryList.size();
                //Begin will add one salary to newSalaryList
                if (newSalaryListSize == 0) {
                    newSalaryList.add(salary);
                }
                //Then filter all latest salary of all employee and pass it to newSalaryList
                else {
                    isSalaryExist = false;
                    for (int i = 0; i < newSalaryListSize; i++) {
                        //If two email equals, check two create date
                        if (newSalaryList.get(i).getEmployee().getEmail()
                                .equals(salary.getEmployee().getEmail())) {
                            isSalaryExist = true;
                            dateBefore = new SimpleDateFormat(Pattern.DATE_PATTERN).parse(
                                    newSalaryList.get(i).getCreatedDate());
                            dateAfter = new SimpleDateFormat(Pattern.DATE_PATTERN).parse(
                                    salary.getCreatedDate());
                            //If date1 issued before date2, set salary from index i become latest salary
                            if (dateBefore.before(dateAfter)) {
                                newSalaryList.set(i, salary);
                                break;
                            }
                        }
                    }
                    //If salary with email haven't existed, add salary to newSalaryList
                    if (!isSalaryExist) {
                        newSalaryList.add(salary);
                    }
                }
            }
        } catch (ParseException e) {
            logger.error("An error occurred: " + e.getMessage());
            throw new ParseDateException(Message.PARSE_DATE_ERROR);
        }

        return newSalaryList;
    }

    /**
     * Create new salary
     *
     * @param salary contains create information
     * @param email  to create salary
     * @return message if create success or not
     */
    public String processCreateNewSalary(EmployeeSalary salary, String email) {
        Employee employee = employeesRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                Message.NOT_EXIST_EMPLOYEE + " with email " + email
                        )
                );

        SimpleDateFormat formatter = new SimpleDateFormat(Pattern.DATE_PATTERN);
        Date issueDate = new Date();
        //Find salary list by employee name
        List<Salary> salaryList = salaryRepository.findByEmployee_FullName(salary.getEmployee());

        Salary oldSalary = findByEmployee_EmailAndCreatedDate(email, formatter.format(issueDate));

        //If there are one salary of an employee create today, we will merge it
        if (oldSalary != null) {
            oldSalary.setEmployee(employee);
            oldSalary.setSalary(salary.getSalary());
            oldSalary.setCreatedDate(formatter.format(issueDate));
            oldSalary.setActive(true);
            salaryRepository.save(oldSalary);

        }
        //else create new salary and set older salary's end date to current date and inactive it
        else {
            Salary newSalary = new Salary();
            newSalary.setEmployee(employee);
            newSalary.setSalary(salary.getSalary());
            newSalary.setCreatedDate(formatter.format(issueDate));
            newSalary.setActive(true);
            salaryRepository.save(newSalary);

            if (salaryList.size() > 0) {
                salaryList.get(salaryList.size() - 1).setEndDate(formatter.format(issueDate));
                salaryList.get(salaryList.size() - 1).setActive(false);
                salaryRepository.save(salaryList.get(salaryList.size() - 1));
            }
        }

        return Message.CREATE_SUCCESS;
    }

    /**
     * Delete salary by id
     *
     * @param id to delete salary
     * @return message if delete success or not
     */
    public String processDeleteSalary(Long id) {
        Salary salary = salaryRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                Message.NOT_EXIST_SALARY + " with id " + id)
                );

        salary.setActive(false);
        salaryRepository.save(salary);

        return Message.DELETE_SUCCESS;
    }

    /***
     * Find salary by id
     * @param id to find salary
     * @return salary by salary id
     */
    public Salary findSalaryById(Long id) {
        return salaryRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                Message.NOT_EXIST_SALARY + " with id " + id)
                );
    }

    /**
     * Find salary list of one employee by employee id
     *
     * @param id to find list
     * @return salary list of an employee by employee id
     */
    public List<EmployeeSalary> findAllSalariesHistoryByEmployeeId(Long id) {
        Employee employee = employeesRepository.findById(id)
                .orElseThrow(() -> new
                                ResourceNotFoundException(
                                Message.NOT_EXIST_EMPLOYEE + " with id " + id
                        )
                );

        List<Salary> salaryList = salaryRepository.findByEmployee_Email(employee.getEmail());
        List<EmployeeSalary> employeeSalaryList = new ArrayList<>();
        EmployeeSalary employeeSalary = null;

        try {
            //Convert salary list to employee salary dto list
            for (Salary salary : salaryList) {
                employeeSalary = new EmployeeSalary();
                if (salary.getCreatedDate() == null) {
                    employeeSalary.setEndDate(salary.getEndDate());
                } else if (salary.getEndDate() == null) {
                    employeeSalary.setCreatedDate(salary.getCreatedDate());
                } else {
                    employeeSalary.setCreatedDate(salary.getCreatedDate());
                    employeeSalary.setEndDate(salary.getEndDate());
                }
                employeeSalary.setEmployee(salary.getEmployee().getFullName());
                employeeSalary.setEmail(salary.getEmployee().getEmail());
                employeeSalary.setSalary(salary.getSalary());
                employeeSalaryList.add(employeeSalary);
            }
        } catch (NullPointerException e) {
            logger.error("An error occurred: " + e.getMessage());
            throw new NullPointerException(Message.NOT_EXIST_SALARY);
        }

        return employeeSalaryList;
    }

    /**
     * Check exist Salary by employee email and createdDate
     *
     * @param email       to check exist
     * @param createdDate to check exist
     * @return true if exist, false if not
     */
    public Boolean checkExistSalaryByEmployeeEmailAndCreatedDate(String email, String createdDate) {
        return salaryRepository.existsByEmployee_EmailAndCreatedDate(email, createdDate);
    }

    /**
     * Find Salary by Employee email and createdDate
     *
     * @param email       to find salary
     * @param createdDate to find salary
     * @return salary by email and create date
     */
    public Salary findByEmployee_EmailAndCreatedDate(String email, String createdDate) {
        return salaryRepository.findByEmployee_EmailAndCreatedDate(email, createdDate);
    }

    /**
     * Update Salary by email and create date
     *
     * @param employeeSalary contains update information
     * @param email          to update salary
     * @param cdDate         to update salary
     * @return message if update success or not
     * @throws ParseException throw ParseException
     */
    public String processUpdateSalary(EmployeeSalary employeeSalary,
                                      String email,
                                      String cdDate)
            throws ParseException {
        Salary salary = findByEmployee_EmailAndCreatedDate(
                email, cdDate);

        try {
            Date createdDate = new SimpleDateFormat(Pattern.DATE_PATTERN)
                    .parse(employeeSalary.getCreatedDate());

            //Check end date is empty or not
            if (!"".equals(employeeSalary.getEndDate())) {
                Date endDate = new SimpleDateFormat(Pattern.DATE_PATTERN)
                        .parse(employeeSalary.getEndDate());

                //Check end date is issued after create date or not
                if (endDate.compareTo(createdDate) < 0) {
                    return Message.VALID_CREATED_DATE_END_DATE;
                }

                salary.setActive(false);
            }

            salary.setSalary(employeeSalary.getSalary());
            salary.setCreatedDate(employeeSalary.getCreatedDate());
            salary.setEndDate(employeeSalary.getEndDate());

            salaryRepository.save(salary);
        } catch (ParseException e) {
            logger.error("An error occurred: " + e.getMessage());
            throw new ParseDateException(Message.PARSE_DATE_ERROR);
        }

        return Message.UPDATE_SUCCESS;
    }

    /**
     * Delete salary by email and create date
     *
     * @param email       to delete salary
     * @param createdDate to delete salary
     * @return me
     */
    public String processDeleteSalary(
            String email,
            String createdDate
    ) {
        Salary salary = findByEmployee_EmailAndCreatedDate(
                email,
                createdDate
        );

        //Check if salary is inactive or not
        if (!salary.isActive()) {
            return Message.DELETE_SALARY_FAIL;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(Pattern.DATE_PATTERN);
        salary.setEndDate(formatter.format(new Date()));
        salary.setActive(false);
        salaryRepository.save(salary);

        return Message.DELETE_SUCCESS;
    }

    /**
     * Find salary list by email
     *
     * @param email to find salary list
     * @return salary list
     */
    public List<Salary> findAllSalaryByEmail(String email) {
        return salaryRepository.findByEmployee_Email(email);
    }
}










