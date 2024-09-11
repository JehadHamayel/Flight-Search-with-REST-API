package com.flight_search.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class that sets up security configurations for the application.
 * It configures the security filter chain, enabling JWT-based stateless authentication.
 * <p>
 *     This configuration disables CSRF, allows public access to certain endpoints, and
 *     ensures that all other requests are authenticated. JWT tokens are validated in the
 *     {@link JwtAuthenticationFilter}.
 * <\p>
 *
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Filter responsible for extracting and validating JWT tokens from HTTP requests.
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Custom {@link AuthenticationProvider} that is responsible for authenticating
     * users using JWT tokens or other mechanisms defined in the application.
     */
    private final AuthenticationProvider authenticationProvider;

    /**
     * Defines the security filter chain that intercepts and processes all incoming HTTP requests.
     * It configures stateless session management, JWT authentication, and URL access restrictions.
     *
     * @param http The {@link HttpSecurity} object that allows configuring web-based security.
     * @return A configured {@link SecurityFilterChain} object.
     * @throws Exception if an error occurs while building the security configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF as this is a stateless, token-based authentication system
                .csrf()
                .disable()

                // Define which endpoints are publicly accessible
                .authorizeHttpRequests()
                .requestMatchers("/auth/**", "/swagger-ui/**")  // Allow access to authentication and Swagger endpoints
                .permitAll()

                // Require authentication for any other endpoint
                .anyRequest()
                .authenticated()

                // Set session management to stateless (no session will be maintained)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // Add custom authentication provider for handling authentication logic
                .and()
                .authenticationProvider(authenticationProvider)

                // Add JWT authentication filter before the UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
