package com.flight_search.services;

import com.flight_search.domain.dto.authentication.AuthenticationRequest;
import com.flight_search.domain.dto.authentication.AuthenticationResponse;
import com.flight_search.domain.dto.authentication.LoginRequest;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest loginRequest) ;

    AuthenticationResponse loginAsUser(LoginRequest loginRequest);

    AuthenticationResponse loginAsAdmin(LoginRequest loginRequest);
}

