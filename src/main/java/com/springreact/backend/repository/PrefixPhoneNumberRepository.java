package com.springreact.backend.repository;

import com.springreact.backend.entity.PrefixPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * PrefixPhoNumberRepository
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
public interface PrefixPhoneNumberRepository extends JpaRepository<PrefixPhoneNumber, Long> {

    /**
     * Check phone prefix exist by title
     *
     * @param title phone prefix title
     * @return true if exist and false otherwise
     */
    boolean existsByTitle(String title);
}
