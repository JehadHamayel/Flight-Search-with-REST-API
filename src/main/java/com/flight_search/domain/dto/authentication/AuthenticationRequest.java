package com.flight_search.domain.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request for authentication containing user credentials.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    /**
     * The email address of the user trying to authenticate.
     */
    private String email;

    /**
     * The password associated with the user's email address.
     */
    private String password;

}
