package com.flight_search.repositories;

import com.flight_search.domain.entity.AirportEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link AirportEntity} entities.
 * Extends {@link CrudRepository} to provide CRUD operations for airport entities.
 */
@Repository
public interface AirportRepository extends CrudRepository<AirportEntity, Long> {

    /**
     * Finds all airports with the specified city name.
     * @param city the city name to search for.
     * @return an iterable collection of {@link AirportEntity} objects matching the city.
     */
    Iterable<AirportEntity> findByCity(String city);

    /**
     * Finds all airport entities with pagination.
     * @param pageable the pagination information.
     * @return a {@link Page} of {@link AirportEntity} objects.
     */
    Page<AirportEntity> findAll(Pageable pageable);

}
