package com.springreact.backend.controller;

import com.springreact.backend.constant.Link;
import com.springreact.backend.entity.PrefixPhoneNumber;
import com.springreact.backend.services.PrefixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PrefixController
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
public class PrefixController {

    private final PrefixService prefixService;

    /**
     * PrefixController constructor(PrefixService)
     *
     * @param prefixService prefixService
     */
    @Autowired
    public PrefixController(PrefixService prefixService) {
        this.prefixService = prefixService;
    }

    /**
     * Get phone number prefix
     *
     * @return phone number prefix list
     */
    @GetMapping(Link.PREFIX_URL)
    public ResponseEntity<List<PrefixPhoneNumber>> getAllPrefix() {
        List<PrefixPhoneNumber> prefixPhoneNumbers = prefixService.findAllPrefixes();

        return new ResponseEntity<>(prefixPhoneNumbers, HttpStatus.OK);
    }

    /**
     * Check exist prefix title
     *
     * @param title to check text from select box is exist in prefix list or not
     * @return true if prefix exist, false if not
     */
    @GetMapping(Link.PREFIX_TITLE_PATH_URL)
    public ResponseEntity<Boolean> checkPrefixTitle(@PathVariable String title) {
        boolean isCheck = prefixService.isPrefixExistByTitle(title);

        if (isCheck) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}




















