package com.flight_search.services;


import com.flight_search.domain.entity.FlightEntity;
import com.flight_search.domain.dto.FlightSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FlightService {

    FlightEntity save(FlightEntity flight);

    List<FlightEntity> findAll();

    Page<FlightEntity> findAll(Pageable pageable);

    Optional<FlightEntity> findById(Long id);

    boolean isExistingFlight(Long id);

    FlightEntity partialUpdate(Long id, FlightEntity FlightEntity);

    void delete(Long id);

    List<FlightEntity> findAllByFlightsInfo(FlightSearchRequest flightSearchRequest);


}
