package com.flight_search.services;

import com.flight_search.domain.dto.authentication.AuthenticationRequest;
import com.flight_search.domain.dto.authentication.AuthenticationResponse;
import com.flight_search.domain.dto.authentication.LoginRequest;

/**
 * Service interface for handling authentication and login operations.
 */
public interface AuthenticationService {

    /**
     * Authenticates a user based on the provided authentication request.
     * @param authenticationRequest the request containing the authentication details.
     * @return an {@link AuthenticationResponse} containing the authentication token.
     */
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    /**
     * Logs in a user with the role of USER and generates an authentication response.
     * @param loginRequest the request containing user login details.
     * @return an {@link AuthenticationResponse} containing the authentication token.
     */
    AuthenticationResponse loginAsUser(LoginRequest loginRequest);

    /**
     * Logs in a user with the role of ADMIN and generates an authentication response.
     * @param loginRequest the request containing admin login details.
     * @return an {@link AuthenticationResponse} containing the authentication token.
     */
    AuthenticationResponse loginAsAdmin(LoginRequest loginRequest);
}
