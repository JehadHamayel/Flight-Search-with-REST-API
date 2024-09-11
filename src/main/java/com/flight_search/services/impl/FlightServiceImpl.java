package com.flight_search.services.impl;

import com.flight_search.domain.entity.AirportEntity;
import com.flight_search.domain.entity.FlightEntity;
import com.flight_search.domain.dto.FlightSearchRequest;
import com.flight_search.repositories.AirportRepository;
import com.flight_search.repositories.FlightRepository;
import com.flight_search.services.FlightService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Implementation of the {@link FlightService} interface.
 * Provides methods for managing flights, including saving, updating, and querying flights.
 */
@Service
@Data
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final JdbcTemplate jdbcTemplate;
    private final AirportRepository airportRepository;

    /**
     * Saves a new flight entity to the repository.
     * @param flight the flight entity to save.
     * @return the saved flight entity.
     */
    @Override
    public FlightEntity save(FlightEntity flight) {
        return flightRepository.save(flight);
    }

    /**
     * Retrieves all flight entities.
     * @return a list of all flight entities.
     */
    @Override
    public List<FlightEntity> findAll() {
        return StreamSupport.stream(flightRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all flight entities with pagination.
     * @param pageable pagination information.
     * @return a page of flight entities.
     */
    @Override
    public Page<FlightEntity> findAll(Pageable pageable) {
        return flightRepository.findAll(pageable);
    }

    /**
     * Retrieves a flight entity by its ID.
     * @param id the ID of the flight entity.
     * @return an Optional containing the flight entity if found, or an empty Optional if not.
     */
    @Override
    public Optional<FlightEntity> findById(Long id) {
        return flightRepository.findById(id);
    }

    /**
     * Checks if a flight entity exists by its ID.
     * @param id the ID of the flight entity.
     * @return true if the flight exists, false otherwise.
     */
    @Override
    public boolean isExistingFlight(Long id) {
        return flightRepository.existsById(id);
    }

    /**
     * Updates an existing flight entity with new data.
     * @param id the ID of the flight entity to update.
     * @param flightEntity the updated flight entity data.
     * @return the updated flight entity.
     * @throws RuntimeException if the flight entity is not found.
     */
    @Override
    public FlightEntity partialUpdate(Long id, FlightEntity flightEntity) {
        flightEntity.setId(id);
        return flightRepository.findById(id)
                .map(existingFlight -> {
                    Optional.ofNullable(flightEntity.getDepartureAirport())
                            .ifPresent(existingFlight::setDepartureAirport);
                    Optional.ofNullable(flightEntity.getArrivalAirport())
                            .ifPresent(existingFlight::setArrivalAirport);
                    Optional.ofNullable(flightEntity.getDepartureDateTime())
                            .ifPresent(existingFlight::setDepartureDateTime);
                    Optional.ofNullable(flightEntity.getReturnDateTime())
                            .ifPresent(existingFlight::setReturnDateTime);
                    Optional.ofNullable(flightEntity.getPrice())
                            .ifPresent(existingFlight::setPrice);
                    return flightRepository.save(existingFlight);
                }).orElseThrow(() -> new RuntimeException("Flight not found"));
    }

    /**
     * Deletes a flight entity by its ID.
     * @param id the ID of the flight entity to delete.
     */
    @Override
    public void delete(Long id) {
        flightRepository.deleteById(id);
    }

    /**
     * Finds flights based on search criteria using a custom SQL query.
     * @param flightSearchRequest contains search criteria for flights.
     * @return a list of flight entities that match the search criteria.
     */
    @Override
    public List<FlightEntity> findAllByFlightsInfo(FlightSearchRequest flightSearchRequest) {
        String sql = buildSqlQuery();

        return jdbcTemplate.query(sql, new FlightRowMapper(),
                flightSearchRequest.getDepartureAirportId(),
                flightSearchRequest.getArrivalAirportId(),
                flightSearchRequest.getDepartureDateTime());
    }

    /**
     * RowMapper implementation to map result set rows to {@link FlightEntity} objects.
     */
    public class FlightRowMapper implements RowMapper<FlightEntity> {

        @Override
        public FlightEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            Optional<AirportEntity> departureAirport = airportRepository.findById(rs.getLong("departure_airport_id"));
            AirportEntity departureAirportEntity = departureAirport.orElseThrow(() -> new SQLException("Departure airport not found"));
            Optional<AirportEntity> arrivalAirport = airportRepository.findById(rs.getLong("arrival_airport_id"));
            AirportEntity arrivalAirportEntity = arrivalAirport.orElseThrow(() -> new SQLException("Arrival airport not found"));

            return FlightEntity.builder()
                    .id(rs.getLong("id"))
                    .departureAirport(new AirportEntity(rs.getLong("departure_airport_id"), departureAirportEntity.getCity()))
                    .arrivalAirport(new AirportEntity(rs.getLong("arrival_airport_id"), arrivalAirportEntity.getCity()))
                    .departureDateTime(rs.getString("departure_date_time"))
                    .returnDateTime(rs.getString("return_date_time"))
                    .price(rs.getDouble("price"))
                    .build();
        }
    }

    /**
     * Builds the SQL query for retrieving flights based on search criteria.
     * @return the SQL query string.
     */
    private String buildSqlQuery() {
        StringBuilder sql = new StringBuilder("SELECT * FROM flights WHERE departure_airport_id = ? AND arrival_airport_id = ? AND departure_date_time = ?");
        return sql.toString();
    }
}
