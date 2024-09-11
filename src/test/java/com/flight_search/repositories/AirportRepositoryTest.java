package com.flight_search.repositories;

import com.flight_search.TestDataUtil;
import com.flight_search.domain.entity.AirportEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the {@link AirportRepository}.
 * <p>
 *     This class tests various functionalities of the {@link AirportRepository} including creating, reading, updating,
 *     deleting, and querying {@link AirportEntity} instances. It uses Spring Boot's testing support to load the application
 *     context and inject dependencies.
 * </p>
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class AirportRepositoryTest {

    private final AirportRepository underTheTest;

    /**
     * Constructor for {@link AirportRepositoryTest}.
     *
     * @param underTheTest The {@link AirportRepository} instance to be tested.
     */
    @Autowired
    public AirportRepositoryTest(AirportRepository underTheTest) {
        this.underTheTest = underTheTest;
    }

    /**
     * Test to verify that an {@link AirportEntity} can be created and recalled from the repository.
     */
    @Test
    public void testThatAirportCanBeCreatedAndRecalled() {
        AirportEntity airportEntity = TestDataUtil.createTestAirportEntityA();
        underTheTest.save(airportEntity);
        Optional<AirportEntity> result = underTheTest.findById(airportEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(airportEntity);
    }

    /**
     * Test to verify that multiple {@link AirportEntity} instances can be created and recalled from the repository.
     */
    @Test
    public void testThatMultipleAirportsCanBeCreatedAndRecalled() {
        AirportEntity airportEntity1 = TestDataUtil.createTestAirportEntityA();
        underTheTest.save(airportEntity1);

        AirportEntity airportEntity2 = TestDataUtil.createTestAirportB();
        underTheTest.save(airportEntity2);

        AirportEntity airportEntity3 = TestDataUtil.createTestAirportC();
        underTheTest.save(airportEntity3);
        Iterable<AirportEntity> airports = underTheTest.findAll();

        assertThat(airports)
                .hasSize(3)
                .containsExactly(airportEntity1, airportEntity2, airportEntity3);
    }

    /**
     * Test to verify that an {@link AirportEntity} can be updated in the repository.
     */
    @Test
    public void testThatAirportCanUpdated() {
        AirportEntity airportEntity = TestDataUtil.createTestAirportEntityA();
        underTheTest.save(airportEntity);
        airportEntity.setCity("New City");
        underTheTest.save(airportEntity);
        Optional<AirportEntity> result = underTheTest.findById(airportEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(airportEntity);
    }

    /**
     * Test to verify that an {@link AirportEntity} can be deleted from the repository.
     */
    @Test
    public void testThatAirportCanBeDeleted() {
        AirportEntity airportEntity = TestDataUtil.createTestAirportEntityA();
        underTheTest.save(airportEntity);
        underTheTest.deleteById(airportEntity.getId());
        Optional<AirportEntity> result = underTheTest.findById(airportEntity.getId());
        assertThat(result).isEmpty();
    }

    /**
     * Test to verify that airports can be retrieved by city.
     */
    @Test
    public void testThatGetAirportsWithCity() {
        AirportEntity airportEntity1 = TestDataUtil.createTestAirportEntityA();
        underTheTest.save(airportEntity1);

        AirportEntity airportEntity2 = TestDataUtil.createTestAirportB();
        underTheTest.save(airportEntity2);

        AirportEntity airportEntity3 = TestDataUtil.createTestAirportC();
        underTheTest.save(airportEntity3);
        Iterable<AirportEntity> airports = underTheTest.findByCity("Amman");

        assertThat(airports)
                .hasSize(1)
                .containsExactly(airportEntity1);
    }
}
