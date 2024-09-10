package com.flight_search.repositories;

import com.flight_search.domain.entity.FlightEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends CrudRepository<FlightEntity, Long> {

    Page<FlightEntity> findAll(Pageable pageable);

}
