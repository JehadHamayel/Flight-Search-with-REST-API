package com.flight_search.domain.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request for logging in, containing user credentials and personal information.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    /**
     * The first name of the user attempting to log in.
     */
    private String firstname;

    /**
     * The last name of the user attempting to log in.
     */
    private String lastname;

    /**
     * The email address of the user attempting to log in.
     */
    private String email;

    /**
     * The password associated with the user's email address.
     */
    private String password;

}
