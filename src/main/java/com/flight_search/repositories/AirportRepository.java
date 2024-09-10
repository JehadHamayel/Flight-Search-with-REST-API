package com.flight_search.repositories;

import com.flight_search.domain.entity.AirportEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AirportRepository extends CrudRepository<AirportEntity, Long> {

    Iterable<AirportEntity> findByCity(String city);

    Page<AirportEntity> findAll(Pageable pageable);

}
