package com.flight_search.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

/**
 * Service interface for handling JWT operations.
 */
public interface JwtService {

    /**
     * Extracts the username from the given JWT token.
     * @param jwtToken the JWT token.
     * @return the username extracted from the token.
     */
    String extractUsername(String jwtToken);

    /**
     * Extracts a specific claim from the given JWT token.
     * @param jwtToken the JWT token.
     * @param claimsResolver a function to resolve the claim from the token's claims.
     * @param <T> the type of the claim to be extracted.
     * @return the extracted claim of type T.
     */
    <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver);

    /**
     * Generates a JWT token for the given user details.
     * @param userDetails the user details for which the token is to be generated.
     * @return the generated JWT token.
     */
    String generateToken(UserDetails userDetails);

    /**
     * Generates a JWT token with extra claims for the given user details.
     * @param extraClaims additional claims to include in the token.
     * @param userDetails the user details for which the token is to be generated.
     * @return the generated JWT token.
     */
    String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    );

    /**
     * Validates the given JWT token based on the user details.
     * @param jwtToken the JWT token to be validated.
     * @param userDetails the user details to validate against.
     * @return true if the token is valid for the given user details, false otherwise.
     */
    boolean isTokenValid(String jwtToken, UserDetails userDetails);
}
