package com.springreact.backend.services;

import com.springreact.backend.constant.Message;
import com.springreact.backend.dto.request.LoginRequest;
import com.springreact.backend.dto.request.SignupRequest;
import com.springreact.backend.dto.response.JwtResponse;
import com.springreact.backend.dto.response.MessageResponse;
import com.springreact.backend.entity.ERole;
import com.springreact.backend.entity.Employee;
import com.springreact.backend.entity.Role;
import com.springreact.backend.repository.EmployeesRepository;
import com.springreact.backend.repository.RoleRepository;
import com.springreact.backend.security.jwt.JwtUtils;
import com.springreact.backend.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * AuthServices
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
public class AuthServices {

    public final static String ADMIN = "ADMIN";
    public final static String MOD = "MOD";

    private final AuthenticationManager authenticationManager;

    private final EmployeesRepository employeesRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    /**
     * AutServices controller(AuthenticationManager, EmployeesRepository, RoleRepository, PasswordEncoder)
     *
     * @param authenticationManager authenticationManager
     * @param employeesRepository   employeesRepository
     * @param roleRepository        roleRepository
     * @param encoder               encoder
     * @param jwtUtils              jwtUtils
     */
    @Autowired
    public AuthServices(AuthenticationManager authenticationManager,
                        EmployeesRepository employeesRepository,
                        RoleRepository roleRepository,
                        PasswordEncoder encoder,
                        JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.employeesRepository = employeesRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Authentication User service
     *
     * @param loginRequest contain username and password
     * @return Return JWT response to save on localstorage
     */
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        //Authentication User by username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        //Set Authentication information
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //Generate new jwt token
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                //item -> item.getAuthority()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles);
    }

    /**
     * Register new user service
     *
     * @param signUpRequest contains some sign up information
     * @return MessageResponse
     */
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        //Check if password equals to confirm password
        if (signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            //Check if email is exist or not
            if (employeesRepository.existsByEmail(signUpRequest.getUsername())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse(Message.USERNAME_HAS_TAKEN));
            }

            Employee employee = new Employee(
                    signUpRequest.getUsername(),
                    encoder.encode(signUpRequest.getPassword())
            );

            Set<String> strRoles = signUpRequest.getRole();
            Set<Role> roles = new HashSet<>();

            //If sign up request not contain role, set default ROLE_USER
            if (strRoles == null) {
                Role userRole = roleRepository.findByRole(ERole.ROLE_USER.name())
                        .orElseThrow(() -> new RuntimeException(Message.NOT_EXIST_ROLE));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case ADMIN:
                            Role adminRole = roleRepository.findByRole(ERole.ROLE_ADMIN.name())
                                    .orElseThrow(() -> new RuntimeException(Message.NOT_EXIST_ROLE));
                            roles.add(adminRole);

                            break;
                        case MOD:
                            Role modRole = roleRepository.findByRole(ERole.ROLE_MODERATOR.name())
                                    .orElseThrow(() -> new RuntimeException(Message.NOT_EXIST_ROLE));
                            roles.add(modRole);

                            break;
                        default:
                            Role userRole = roleRepository.findByRole(ERole.ROLE_USER.name())
                                    .orElseThrow(() -> new RuntimeException(Message.NOT_EXIST_ROLE));
                            roles.add(userRole);
                    }
                });
            }

            employee.setRole(roles.iterator().next());
            employee.setActive(true);
            employeesRepository.save(employee);

            return ResponseEntity.ok(new MessageResponse(Message.REGISTER_SUCCESS));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(Message.VALID_CONFIRM_PASSWORD));
        }
    }
}
