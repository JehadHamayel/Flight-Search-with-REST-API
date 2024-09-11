package com.flight_search.config;

import com.flight_search.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * Configuration class for setting up beans required for the application.
 * This includes configurations for JDBC, REST template, security, and authentication.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    /**
     * Provides a {@link JdbcTemplate} bean configured with the specified {@link DataSource}.
     *
     * @param dataSource the {@link DataSource} used to configure the {@link JdbcTemplate}
     * @return a {@link JdbcTemplate} instance
     */
    @Bean
    public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * Provides a {@link RestTemplate} bean for making HTTP requests.
     *
     * @return a {@link RestTemplate} instance
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Provides a {@link UserDetailsService} bean for loading user-specific data.
     * It uses the {@link UserRepository} to find a user by email.
     *
     * @return a {@link UserDetailsService} instance
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Provides an {@link AuthenticationProvider} bean for handling authentication.
     * It uses {@link DaoAuthenticationProvider} configured with {@link UserDetailsService} and {@link PasswordEncoder}.
     *
     * @return an {@link AuthenticationProvider} instance
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Provides an {@link AuthenticationManager} bean used for authentication.
     *
     * @param config the {@link AuthenticationConfiguration} used to configure the {@link AuthenticationManager}
     * @return an {@link AuthenticationManager} instance
     * @throws Exception if an error occurs while creating the {@link AuthenticationManager}
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Provides a {@link PasswordEncoder} bean for encoding passwords.
     * It uses {@link BCryptPasswordEncoder} for secure password hashing.
     *
     * @return a {@link PasswordEncoder} instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
