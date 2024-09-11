package com.flight_search.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up beans related to object mapping.
 * This includes configurations for Jackson's ObjectMapper and ModelMapper.
 */
@Configuration
public class MapperConfig {

    /**
     * Provides an {@link ObjectMapper} bean for JSON serialization and deserialization.
     *
     * @return an {@link ObjectMapper} instance
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    /**
     * Provides a {@link ModelMapper} bean for object mapping and transformation.
     * Configures the {@link ModelMapper} to use the LOOSE matching strategy.
     *
     * @return a {@link ModelMapper} instance
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }

}
