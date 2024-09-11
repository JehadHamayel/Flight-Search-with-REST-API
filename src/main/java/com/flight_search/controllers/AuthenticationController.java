package com.flight_search.controllers;

import com.flight_search.domain.dto.authentication.AuthenticationRequest;
import com.flight_search.domain.dto.authentication.AuthenticationResponse;
import com.flight_search.domain.dto.authentication.LoginRequest;
import com.flight_search.services.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication-related requests.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationServiceImpl service;

    /**
     * Handles user login requests.
     *
     * @param loginRequest contains the user's login credentials, including first name and last name.
     * @return a {@link ResponseEntity} containing an {@link AuthenticationResponse} with the authentication details.
     */
    @PostMapping("/loginAsUser")
    public ResponseEntity<AuthenticationResponse> loginAsUser(
            @RequestBody LoginRequest loginRequest
    ) {
        log.info("User login attempt with username: {}", loginRequest.getFirstname() + " " + loginRequest.getLastname());
        AuthenticationResponse response = service.loginAsUser(loginRequest);
        log.info("User login successful for username: {}", loginRequest.getFirstname() + " " + loginRequest.getLastname());
        return ResponseEntity.ok(response);
    }

    /**
     * Handles admin login requests.
     *
     * @param loginRequest contains the admin's login credentials, including first name and last name.
     * @return a {@link ResponseEntity} containing an {@link AuthenticationResponse} with the authentication details.
     */
    @PostMapping("/loginAsAdmin")
    public ResponseEntity<AuthenticationResponse> loginAsAdmin(
            @RequestBody LoginRequest loginRequest
    ) {
        log.info("Admin login attempt with username: {}", loginRequest.getFirstname() + " " + loginRequest.getLastname());
        AuthenticationResponse response = service.loginAsAdmin(loginRequest);
        log.info("Admin login successful for username: {}", loginRequest.getFirstname() + " " + loginRequest.getLastname());
        return ResponseEntity.ok(response);
    }

    /**
     * Handles general authentication requests.
     *
     * @param authRequest contains the user's email for authentication.
     * @return a {@link ResponseEntity} containing an {@link AuthenticationResponse} with the authentication details.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authRequest
    ) {
        log.info("Authentication attempt for email: {}", authRequest.getEmail());
        AuthenticationResponse response = service.authenticate(authRequest);
        log.info("Authentication successful for email: {}", authRequest.getEmail());
        return ResponseEntity.ok(response);
    }
}
