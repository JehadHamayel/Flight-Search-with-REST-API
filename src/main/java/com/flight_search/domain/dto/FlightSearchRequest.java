package com.flight_search.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSearchRequest {

    @NotNull
    private Long departureAirportId;

    @NotNull
    private Long arrivalAirportId;

    @NotNull
    private String departureDateTime;

    private String returnDateTime;
}
