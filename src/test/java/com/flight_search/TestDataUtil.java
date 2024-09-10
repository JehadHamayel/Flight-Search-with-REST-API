package com.flight_search;

import com.flight_search.domain.dto.AirportDto;
import com.flight_search.domain.entity.AirportEntity;
import com.flight_search.domain.entity.UserEntity;
import com.flight_search.enums.Role;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class TestDataUtil {

    public static UserEntity createTestUserEntityA() {
        return UserEntity.builder()
                .id(1L)
                .firstname("Jehad")
                .lastname("Hamayel")
                .email("JehadHamayel@gmail.com")
                .password("123456")
                .role(Role.USER)
                .build();
    }

    public static AirportEntity createTestAirportEntityA() {
        return AirportEntity.builder()
                .id(1L)
                .city("Amman")
                .build();
    }

    public static AirportDto createTestAirportDtoA() {
        return AirportDto.builder()
                .id(4L)
                .city("Amman")
                .build();
    }

    public static AirportEntity createTestAirportB() {
        return AirportEntity.builder()
                .id(2L)
                .city("Madaba")
                .build();
    }

    public static AirportEntity createTestAirportC() {
        return AirportEntity.builder()
                .id(3L)
                .city("Irbid")
                .build();
    }

}
