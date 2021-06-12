package com.springreact.backend.controller;

import com.springreact.backend.constant.Link;
import com.springreact.backend.dto.request.LoginRequest;
import com.springreact.backend.dto.request.SignupRequest;
import com.springreact.backend.dto.response.JwtResponse;
import com.springreact.backend.services.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController
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
public class AuthController {

    private final AuthServices authServices;

    /**
     * AuthController constructor(AuthServices)
     *
     * @param authServices authServices
     */
    @Autowired
    public AuthController(AuthServices authServices) {
        this.authServices = authServices;
    }

    /**
     * Login
     *
     * @param loginRequest contain username and password
     * @return Return JWT response to save on localstorage
     */
    @PostMapping(Link.SIGN_IN)
    public ResponseEntity<?> login(
            @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authServices.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    /**
     * SignUp new User
     *
     * @param signUpRequest contains some sign up information
     * @return MessageResponse
     */
    @PostMapping(Link.SIGN_UP)
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signUpRequest) {
        return authServices.registerUser(signUpRequest);
    }
}











