package com.flight_search.services;

import com.flight_search.domain.entity.FlightEntity;
import com.flight_search.domain.dto.FlightSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing flight-related operations.
 */
public interface FlightService {

    /**
     * Saves a new or existing flight entity.
     * @param flight the flight entity to be saved.
     * @return the saved flight entity.
     */
    FlightEntity save(FlightEntity flight);

    /**
     * Retrieves all flight entities.
     * @return a list of all flight entities.
     */
    List<FlightEntity> findAll();

    /**
     * Retrieves a paginated list of flight entities.
     * @param pageable the pagination information.
     * @return a page of flight entities.
     */
    Page<FlightEntity> findAll(Pageable pageable);

    /**
     * Retrieves a flight entity by its ID.
     * @param id the ID of the flight entity.
     * @return an optional containing the flight entity if found, or empty if not found.
     */
    Optional<FlightEntity> findById(Long id);

    /**
     * Checks if a flight entity exists by its ID.
     * @param id the ID of the flight entity.
     * @return true if the flight exists, false otherwise.
     */
    boolean isExistingFlight(Long id);

    /**
     * Partially updates an existing flight entity.
     * @param id the ID of the flight entity to be updated.
     * @param flightEntity the flight entity with updated information.
     * @return the updated flight entity.
     */
    FlightEntity partialUpdate(Long id, FlightEntity flightEntity);

    /**
     * Deletes a flight entity by its ID.
     * @param id the ID of the flight entity to be deleted.
     */
    void delete(Long id);

    /**
     * Retrieves a list of flight entities based on search criteria.
     * @param flightSearchRequest the request containing search criteria for flights.
     * @return a list of flight entities matching the search criteria.
     */
    List<FlightEntity> findAllByFlightsInfo(FlightSearchRequest flightSearchRequest);
}
