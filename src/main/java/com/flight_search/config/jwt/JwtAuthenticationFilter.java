package com.flight_search.config.jwt;

import com.flight_search.services.impl.JwtServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * This filter is responsible for handling JWT (JSON Web Token) authentication.
 * It intercepts each HTTP request to check if it contains a valid JWT token in the Authorization header.
 * If a valid token is found, it sets the corresponding authentication in the Spring Security context.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Service responsible for extracting and validating JWT tokens.
     */
    private final JwtServiceImpl jwtServiceImpl;

    /**
     * Service responsible for loading user-specific data during authentication.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Filters each incoming request to check for a valid JWT token in the Authorization header.
     * If the token is valid, it sets the authentication in the {@link SecurityContextHolder}.
     *
     * @param request     the current HTTP request.
     * @param response    the current HTTP response.
     * @param filterChain the current filter chain that handles the request.
     * @throws ServletException if an error occurs during the request processing.
     * @throws IOException      if an input/output error occurs during request processing.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        // Check if the Authorization header contains a valid JWT token
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the Authorization header
        jwtToken = authorizationHeader.substring(7);
        userEmail = jwtServiceImpl.extractUsername(jwtToken);

        // Check if the user is not already authenticated
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Validate the JWT token and set the authentication if valid
            if (jwtServiceImpl.isTokenValid(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Set the authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
