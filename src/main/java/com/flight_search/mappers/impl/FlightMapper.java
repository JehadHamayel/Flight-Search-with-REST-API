package com.flight_search.mappers.impl;

import com.flight_search.domain.dto.AirportDto;
import com.flight_search.domain.dto.FlightDto;
import com.flight_search.domain.entity.AirportEntity;
import com.flight_search.domain.entity.FlightEntity;
import com.flight_search.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlightMapper implements Mapper<FlightEntity, FlightDto> {

    private final ModelMapper modelMapper;

    @Override
    public FlightDto mapTo(FlightEntity flightEntity) {
        return modelMapper.map(flightEntity, FlightDto.class);
    }

    @Override
    public FlightEntity mapFrom(FlightDto flightDto) {
        return modelMapper.map(flightDto, FlightEntity.class);
    }
}
