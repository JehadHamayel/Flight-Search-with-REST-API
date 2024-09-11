package com.flight_search.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for representing a flight.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightDto {

    /**
     * The unique identifier of the flight.
     */
    private Long id;

    /**
     * The departure airport information.
     */
    private AirportDto departureAirport;

    /**
     * The arrival airport information.
     */
    private AirportDto arrivalAirport;

    /**
     * The date and time when the flight departs.
     */
    private String departureDateTime;

    /**
     * The date and time when the flight returns (if it's a round trip).
     */
    private String returnDateTime;

    /**
     * The price of the flight.
     */
    private Double price;

}
