package com.flight_search.services.impl;

import com.flight_search.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Implementation of the {@link JwtService} interface.
 * Provides methods for generating, validating, and extracting information from JSON Web Tokens (JWTs).
 */
@Service
public class JwtServiceImpl implements JwtService {

    private static final String SECRET_KEY = "EC70C9A9F35D2B4D1CFA431E76A2B8D9E5CB10F77F6D39AC5BE4F62EA76E3281";

    /**
     * Extracts the username from a JWT token.
     * @param jwtToken the JWT token from which to extract the username.
     * @return the username extracted from the JWT token.
     */
    @Override
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token using a provided function.
     * @param jwtToken the JWT token from which to extract the claim.
     * @param claimsResolver a function to extract the claim.
     * @param <T> the type of the claim to be extracted.
     * @return the extracted claim.
     */
    @Override
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for a user with default claims.
     * @param userDetails the user details to include in the token.
     * @return the generated JWT token.
     */
    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with custom claims.
     * @param extraClaims additional claims to include in the token.
     * @param userDetails the user details to include in the token.
     * @return the generated JWT token.
     */
    @Override
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates a JWT token by checking if it is expired and if the username matches.
     * @param jwtToken the JWT token to validate.
     * @param userDetails the user details to compare against the token.
     * @return true if the token is valid, false otherwise.
     */
    @Override
    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);
    }

    /**
     * Checks if the JWT token is expired.
     * @param jwtToken the JWT token to check.
     * @return true if the token is expired, false otherwise.
     */
    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    /**
     * Extracts the expiration date of the JWT token.
     * @param jwtToken the JWT token from which to extract the expiration date.
     * @return the expiration date of the JWT token.
     */
    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the JWT token.
     * @param jwtToken the JWT token from which to extract claims.
     * @return the claims extracted from the JWT token.
     */
    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    /**
     * Generates the signing key used for signing and verifying JWT tokens.
     * @return the signing key.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
