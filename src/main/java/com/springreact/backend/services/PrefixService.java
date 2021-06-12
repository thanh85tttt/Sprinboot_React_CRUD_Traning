package com.springreact.backend.services;

import com.springreact.backend.constant.Message;
import com.springreact.backend.entity.PrefixPhoneNumber;
import com.springreact.backend.exception.ResourceNotFoundException;
import com.springreact.backend.repository.PrefixPhoneNumberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PrefixService
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
public class PrefixService {

    private final PrefixPhoneNumberRepository prefixPhoneNumberRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * PrefixService controller(PrefixPhoNumberRepository)
     * @param prefixPhoneNumberRepository prefixPhoneNumberRepository
     */
    @Autowired
    public PrefixService(PrefixPhoneNumberRepository prefixPhoneNumberRepository) {
        this.prefixPhoneNumberRepository = prefixPhoneNumberRepository;
    }

    public List<PrefixPhoneNumber> findAllPrefixes(){
        List<PrefixPhoneNumber> prefixPhoneNumbers = null;

        try {
            prefixPhoneNumbers = prefixPhoneNumberRepository.findAll();
        } catch (NullPointerException e) {
            logger.error("An error occur: " + e.getMessage());
            throw new ResourceNotFoundException(Message.NOT_EXIST_PREFIX_LIST);
        }

        return prefixPhoneNumbers;
    }

    /**
     * Check exist prefix title
     *
     * @param title to check text from select box is exist in prefix list or not
     * @return true if prefix exist, false if not
     */
    public Boolean isPrefixExistByTitle(String title){
        return prefixPhoneNumberRepository.existsByTitle(title);
    }
}
