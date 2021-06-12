package com.springreact.backend.security;

import com.springreact.backend.security.jwt.AuthEntryPointJwt;
import com.springreact.backend.security.jwt.AuthTokenFilter;
import com.springreact.backend.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * WebSecurityConfig
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
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    private final AuthEntryPointJwt unauthorizedHandler;

    /**
     * WebSecurityConfig constructor(UserDetailsServiceImpl, AuthEntryPointJwt)
     *
     * @param userDetailsService  userDetailsService
     * @param unauthorizedHandler unauthorizedHandler
     */
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService,
                             AuthEntryPointJwt unauthorizedHandler) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    /**
     * Filter JWT
     *
     * @return new AuthTokenFilter
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    /**
     * Configure authenticationManagerBuilder
     *
     * @param authenticationManagerBuilder AuthenticationManagerBuilder
     * @throws Exception throw Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * authenticationManagerBean
     *
     * @return authenticationManagerBean
     * @throws Exception throw Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean()
            throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * BCryptPasswordEncoder
     *
     * @return new BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configure HttpSecurity
     *
     * @param http to configure security for web app
     * @throws Exception throw Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                //Handling exception if JWT unauthorized
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                //Not using session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER).and()
                //Permit all authentication api
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/v1/**").permitAll()
                .anyRequest().authenticated();

        //JWT Filter
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}














