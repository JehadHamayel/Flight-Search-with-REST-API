package com.flight_search.mappers.impl;

import com.flight_search.domain.dto.AirportDto;
import com.flight_search.domain.entity.AirportEntity;
import com.flight_search.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Provides mapping functionality between {@link AirportEntity} and {@link AirportDto}.
 * This class uses {@link ModelMapper} to convert between the entity and DTO representations of an airport.
 */
@Component
@RequiredArgsConstructor
public class AirportMapper implements Mapper<AirportEntity, AirportDto> {

    private final ModelMapper modelMapper;

    /**
     * Converts an {@link AirportEntity} to an {@link AirportDto}.
     * @param airportEntity the entity to be converted.
     * @return the corresponding DTO.
     */
    @Override
    public AirportDto mapTo(AirportEntity airportEntity) {
        return modelMapper.map(airportEntity, AirportDto.class);
    }

    /**
     * Converts an {@link AirportDto} to an {@link AirportEntity}.
     * @param airportDto the DTO to be converted.
     * @return the corresponding entity.
     */
    @Override
    public AirportEntity mapFrom(AirportDto airportDto) {
        return modelMapper.map(airportDto, AirportEntity.class);
    }
}
