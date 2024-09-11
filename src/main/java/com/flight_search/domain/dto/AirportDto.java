package com.flight_search.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for representing an airport.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportDto {

    /**
     * The unique identifier of the airport.
     */
    private Long id;

    /**
     * The city where the airport is located.
     */
    private String city;

}
