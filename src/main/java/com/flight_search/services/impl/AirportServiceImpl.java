package com.flight_search.services.impl;


import com.flight_search.domain.dto.AirportDto;
import com.flight_search.domain.entity.AirportEntity;
import com.flight_search.repositories.AirportRepository;
import com.flight_search.services.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    @Override
    public AirportEntity save(Long id, AirportEntity airport) {
        airport.setId(id);
        return airportRepository.save(airport);
    }

    @Override
    public List<AirportEntity> findAll() {
        return StreamSupport.stream(airportRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<AirportEntity> findAll(Pageable pageable) {
        return airportRepository.findAll(pageable);
    }

    @Override
    public Optional<AirportEntity> findById(Long id) {
        return airportRepository.findById(id);
    }

    @Override
    public boolean isExistingAirport(Long id) {
        return airportRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        airportRepository.deleteById(id);
    }

}

