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

/**
 * Implementation of the {@link AirportService} interface.
 * Provides business logic for managing {@link AirportEntity} entities.
 */
@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    /**
     * Saves an {@link AirportEntity} with the given ID.
     * If an entity with the provided ID already exists, it will be updated.
     * @param id the ID of the airport to be saved.
     * @param airport the {@link AirportEntity} to be saved.
     * @return the saved {@link AirportEntity}.
     */
    @Override
    public AirportEntity save(Long id, AirportEntity airport) {
        airport.setId(id);
        return airportRepository.save(airport);
    }

    /**
     * Retrieves all {@link AirportEntity} entities.
     * @return a list of all {@link AirportEntity} entities.
     */
    @Override
    public List<AirportEntity> findAll() {
        return StreamSupport.stream(airportRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all {@link AirportEntity} entities with pagination.
     * @param pageable the pagination information.
     * @return a {@link Page} of {@link AirportEntity} entities.
     */
    @Override
    public Page<AirportEntity> findAll(Pageable pageable) {
        return airportRepository.findAll(pageable);
    }

    /**
     * Finds an {@link AirportEntity} by its ID.
     * @param id the ID of the airport to be found.
     * @return an {@link Optional} containing the {@link AirportEntity} if found, or {@link Optional#empty()} if not.
     */
    @Override
    public Optional<AirportEntity> findById(Long id) {
        return airportRepository.findById(id);
    }

    /**
     * Checks if an {@link AirportEntity} with the given ID exists.
     * @param id the ID to check.
     * @return true if the entity exists, false otherwise.
     */
    @Override
    public boolean isExistingAirport(Long id) {
        return airportRepository.existsById(id);
    }

    /**
     * Deletes an {@link AirportEntity} by its ID.
     * @param id the ID of the airport to be deleted.
     */
    @Override
    public void delete(Long id) {
        airportRepository.deleteById(id);
    }

}
