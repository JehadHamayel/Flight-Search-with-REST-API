package com.flight_search.mappers.impl;

import com.flight_search.domain.dto.AirportDto;
import com.flight_search.domain.entity.AirportEntity;
import com.flight_search.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AirportMapper implements Mapper<AirportEntity, AirportDto> {

    private final ModelMapper modelMapper;

    @Override
    public AirportDto mapTo(AirportEntity airportEntity) {
        return modelMapper.map(airportEntity, AirportDto.class);
    }

    @Override
    public AirportEntity mapFrom(AirportDto airportDto) {
        return modelMapper.map(airportDto, AirportEntity.class);
    }
}
