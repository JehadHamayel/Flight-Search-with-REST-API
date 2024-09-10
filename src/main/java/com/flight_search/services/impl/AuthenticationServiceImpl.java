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

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


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
