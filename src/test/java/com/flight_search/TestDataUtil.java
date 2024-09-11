package com.flight_search;

import com.flight_search.domain.dto.AirportDto;
import com.flight_search.domain.entity.AirportEntity;
import com.flight_search.domain.entity.UserEntity;
import com.flight_search.enums.Role;
import lombok.NoArgsConstructor;

/**
 * A utility class for creating test data for entities and DTOs.
 * <p>
 * This class provides static methods to create instances of {@link UserEntity} and {@link AirportEntity},
 * as well as a sample {@link AirportDto}. These methods are intended for use in unit tests and other testing scenarios
 * where sample data is required.
 * </p>
 * <p>
 * The class is marked as {@code final} to prevent subclassing and has a private constructor to prevent instantiation.
 * </p>
 */
@NoArgsConstructor
public final class TestDataUtil {

    /**
     * Creates a test instance of {@link UserEntity} with predefined values.
     *
     * @return A {@link UserEntity} object with sample data, including ID, first name, last name, email, password, and role.
     */
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

    /**
     * Creates a test instance of {@link AirportEntity} with predefined values.
     *
     * @return An {@link AirportEntity} object with sample data, including ID and city.
     */
    public static AirportEntity createTestAirportEntityA() {
        return AirportEntity.builder()
                .id(1L)
                .city("Amman")
                .build();
    }

    /**
     * Creates a test instance of {@link AirportDto} with predefined values.
     *
     * @return An {@link AirportDto} object with sample data, including ID and city.
     */
    public static AirportDto createTestAirportDtoA() {
        return AirportDto.builder()
                .id(4L)
                .city("Amman")
                .build();
    }

    /**
     * Creates a test instance of {@link AirportEntity} with predefined values.
     *
     * @return An {@link AirportEntity} object with sample data for the city "Madaba".
     */
    public static AirportEntity createTestAirportB() {
        return AirportEntity.builder()
                .id(2L)
                .city("Madaba")
                .build();
    }

    /**
     * Creates a test instance of {@link AirportEntity} with predefined values.
     *
     * @return An {@link AirportEntity} object with sample data for the city "Irbid".
     */
    public static AirportEntity createTestAirportC() {
        return AirportEntity.builder()
                .id(3L)
                .city("Irbid")
                .build();
    }

}
