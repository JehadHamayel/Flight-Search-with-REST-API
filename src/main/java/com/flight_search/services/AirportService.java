package com.flight_search.services;

import com.flight_search.domain.entity.AirportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing airport entities.
 */
public interface AirportService {

    /**
     * Saves an airport entity with the given ID.
     * @param id the ID to set for the airport entity.
     * @param airport the airport entity to save.
     * @return the saved airport entity.
     */
    AirportEntity save(Long id, AirportEntity airport);

    /**
     * Retrieves all airport entities.
     * @return a list of all airport entities.
     */
    List<AirportEntity> findAll();

    /**
     * Retrieves all airport entities with pagination.
     * @param pageable the pagination information.
     * @return a page of airport entities.
     */
    Page<AirportEntity> findAll(Pageable pageable);

    /**
     * Finds an airport entity by its ID.
     * @param id the ID of the airport entity to find.
     * @return an Optional containing the found airport entity, or empty if not found.
     */
    Optional<AirportEntity> findById(Long id);

    /**
     * Checks if an airport entity with the given ID exists.
     * @param id the ID of the airport entity to check.
     * @return true if the airport entity exists, false otherwise.
     */
    boolean isExistingAirport(Long id);

    /**
     * Deletes an airport entity by its ID.
     * @param id the ID of the airport entity to delete.
     */
    void delete(Long id);
}
