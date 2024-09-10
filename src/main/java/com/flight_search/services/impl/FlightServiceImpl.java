package com.flight_search.services.impl;


import com.flight_search.domain.entity.AirportEntity;
import com.flight_search.domain.entity.FlightEntity;
import com.flight_search.domain.dto.FlightSearchRequest;
import com.flight_search.repositories.AirportRepository;
import com.flight_search.repositories.FlightRepository;
import com.flight_search.services.FlightService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Arrays.stream;
@Service
@Data
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final JdbcTemplate jdbcTemplate;
    private final AirportRepository airportRepository;

    @Override
    public FlightEntity save(FlightEntity flight) {
        return flightRepository.save(flight);
    }

    @Override
    public List<FlightEntity> findAll() {
        return StreamSupport.stream(flightRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<FlightEntity> findAll(Pageable pageable) {
        return flightRepository.findAll(pageable);
    }

    @Override
    public Optional<FlightEntity> findById(Long id) {
        return flightRepository.findById(id);
    }

    @Override
    public boolean isExistingFlight(Long id) {
        return flightRepository.existsById(id);
    }

    @Override
    public FlightEntity partialUpdate(Long id, FlightEntity alightEntity) {
        alightEntity.setId(id);
        return flightRepository.findById(id)
                .map(alights -> {
                    Optional.ofNullable(alightEntity.getDepartureAirport())
                            .ifPresent(alights::setDepartureAirport);
                    Optional.ofNullable(alightEntity.getArrivalAirport())
                            .ifPresent(alights::setArrivalAirport);
                    Optional.ofNullable(alightEntity.getDepartureDateTime())
                            .ifPresent(alights::setDepartureDateTime);
                    Optional.ofNullable(alightEntity.getReturnDateTime())
                            .ifPresent(alights::setReturnDateTime);
                    Optional.ofNullable(alightEntity.getPrice())
                            .ifPresent(alights::setPrice);
                    return flightRepository.save(alights);
                }).orElseThrow(() -> new RuntimeException("Flight not found"));
    }

    @Override
    public void delete(Long id) {
        flightRepository.deleteById(id);
    }

    @Override
    public List<FlightEntity> findAllByFlightsInfo(FlightSearchRequest flightSearchRequest) {
        String sql = buildSqlQuery();

        return jdbcTemplate.query(sql, new FlightRowMapper(),
                flightSearchRequest.getDepartureAirportId(),
                flightSearchRequest.getArrivalAirportId(),
                flightSearchRequest.getDepartureDateTime());


    }

    public class FlightRowMapper implements RowMapper<FlightEntity> {

        @Override
        public FlightEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            Optional<AirportEntity> departureAirport = airportRepository.findById(rs.getLong("departure_airport_id"));
            AirportEntity departureAirportEntity = departureAirport.stream().toList().get(0);
            Optional<AirportEntity> arrivalAirport = airportRepository.findById(rs.getLong("arrival_airport_id"));
            AirportEntity arrivalAirportEntity = arrivalAirport.stream().toList().get(0);

            return FlightEntity.builder()
                    .id(rs.getLong("id"))
                    .departureAirport(new AirportEntity(rs.getLong("departure_airport_id"),departureAirportEntity.getCity()))
                    .arrivalAirport(new AirportEntity(rs.getLong("arrival_airport_id"), arrivalAirportEntity.getCity()))
                    .departureDateTime(rs.getString("departure_date_time"))
                    .returnDateTime(rs.getString("return_date_time"))
                    .price(rs.getDouble("price"))
                    .build();

        }
    }

    private String buildSqlQuery() {
        StringBuilder sql = new StringBuilder("SELECT * FROM flights WHERE departure_airport_id = ? AND arrival_airport_id = ? AND departure_date_time = ?");
        return sql.toString();
    }

}

