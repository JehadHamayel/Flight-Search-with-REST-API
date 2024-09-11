package com.flight_search.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request for searching flights based on user criteria.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSearchRequest {

    /**
     * The unique identifier of the departure airport.
     * This field is required.
     */
    @NotNull
    private Long departureAirportId;

    /**
     * The unique identifier of the arrival airport.
     * This field is required.
     */
    @NotNull
    private Long arrivalAirportId;

    /**
     * The date and time when the flight departs.
     * This field is required.
     */
    @NotNull
    private String departureDateTime;

    /**
     * The date and time when the flight returns (if it's a round trip).
     * This field is optional.
     */
    private String returnDateTime;

}
