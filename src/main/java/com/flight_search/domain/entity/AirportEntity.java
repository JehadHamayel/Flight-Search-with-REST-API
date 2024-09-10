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
@Table(name = "airports")
public class AirportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "airports_seq")
    @SequenceGenerator(name = "airports_seq", sequenceName = "custom_airports_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    private String city;

}
