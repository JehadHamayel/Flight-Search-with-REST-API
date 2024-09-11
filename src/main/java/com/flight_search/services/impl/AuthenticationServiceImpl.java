package com.flight_search.services.impl;

import com.flight_search.domain.dto.authentication.AuthenticationRequest;
import com.flight_search.domain.dto.authentication.AuthenticationResponse;
import com.flight_search.domain.dto.authentication.LoginRequest;
import com.flight_search.domain.entity.UserEntity;
import com.flight_search.enums.Role;
import com.flight_search.repositories.UserRepository;
import com.flight_search.services.AuthenticationService;
import com.flight_search.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link AuthenticationService} interface.
 * Provides methods for user authentication and JWT token generation.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user with USER role and generates a JWT token for the user.
     * @param loginRequest contains user details for registration and login.
     * @return an {@link AuthenticationResponse} containing the generated JWT token.
     */
    @Override
    public AuthenticationResponse loginAsUser(LoginRequest loginRequest) {
        var user = UserEntity.builder()
                .firstname(loginRequest.getFirstname())
                .lastname(loginRequest.getLastname())
                .email(loginRequest.getEmail())
                .password(passwordEncoder.encode(loginRequest.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Registers a new user with ADMIN role and generates a JWT token for the user.
     * @param loginRequest contains user details for registration and login.
     * @return an {@link AuthenticationResponse} containing the generated JWT token.
     */
    @Override
    public AuthenticationResponse loginAsAdmin(LoginRequest loginRequest) {
        var user = UserEntity.builder()
                .firstname(loginRequest.getFirstname())
                .lastname(loginRequest.getLastname())
                .email(loginRequest.getEmail())
                .password(passwordEncoder.encode(loginRequest.getPassword()))
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Authenticates an existing user and generates a JWT token if authentication is successful.
     * @param loginRequest contains email and password for authentication.
     * @return an {@link AuthenticationResponse} containing the generated JWT token.
     * @throws RuntimeException if the user is not found.
     */
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
