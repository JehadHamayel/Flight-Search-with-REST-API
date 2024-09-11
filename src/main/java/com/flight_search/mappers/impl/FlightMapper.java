package com.flight_search.mappers.impl;

import com.flight_search.domain.dto.FlightDto;
import com.flight_search.domain.entity.FlightEntity;
import com.flight_search.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Provides mapping functionality between {@link FlightEntity} and {@link FlightDto}.
 * This class uses {@link ModelMapper} to convert between the entity and DTO representations of a flight.
 */
@Component
@RequiredArgsConstructor
public class FlightMapper implements Mapper<FlightEntity, FlightDto> {

    private final ModelMapper modelMapper;

    /**
     * Converts a {@link FlightEntity} to a {@link FlightDto}.
     * @param flightEntity the entity to be converted.
     * @return the corresponding DTO.
     */
    @Override
    public FlightDto mapTo(FlightEntity flightEntity) {
        return modelMapper.map(flightEntity, FlightDto.class);
    }

    /**
     * Converts a {@link FlightDto} to a {@link FlightEntity}.
     * @param flightDto the DTO to be converted.
     * @return the corresponding entity.
     */
    @Override
    public FlightEntity mapFrom(FlightDto flightDto) {
        return modelMapper.map(flightDto, FlightEntity.class);
    }
}
