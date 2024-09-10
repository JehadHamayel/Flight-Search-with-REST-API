package com.flight_search.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

import java.util.Collection;

@Profile("test")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "flights")
public class FlightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flights_seq")
    @SequenceGenerator(name = "flights_seq", sequenceName = "custom_flights_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "departure_airport_id", referencedColumnName = "id")
    @NotNull
    private AirportEntity departureAirport;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "arrival_airport_id", referencedColumnName = "id")
    @NotNull
    private AirportEntity arrivalAirport;

    @NotNull
    private String departureDateTime;

    private String returnDateTime;

    @NotNull
    private Double price;

}
