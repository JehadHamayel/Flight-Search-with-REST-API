package com.flight_search.services;


import com.flight_search.domain.entity.AirportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AirportService {

    AirportEntity save(Long id, AirportEntity airport);

    List<AirportEntity> findAll();

    Page<AirportEntity> findAll(Pageable pageable);

    Optional<AirportEntity> findById(Long id);

    boolean isExistingAirport(Long id);

    void delete(Long id);

}
