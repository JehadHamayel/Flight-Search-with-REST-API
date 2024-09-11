package com.flight_search.repositories;

import com.flight_search.domain.entity.FlightEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link FlightEntity} entities.
 * Extends {@link CrudRepository} to provide CRUD operations for flight entities.
 */
@Repository
public interface FlightRepository extends CrudRepository<FlightEntity, Long> {

    /**
     * Finds all flight entities with pagination.
     * @param pageable the pagination information.
     * @return a {@link Page} of {@link FlightEntity} objects.
     */
    Page<FlightEntity> findAll(Pageable pageable);

}
