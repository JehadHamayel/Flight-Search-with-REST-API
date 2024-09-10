package com.flight_search.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightDto {

    private Long id;

    private AirportDto departureAirport;

    private AirportDto arrivalAirport;

    private String departureDateTime;

    private String returnDateTime;

    private Double price;


}
