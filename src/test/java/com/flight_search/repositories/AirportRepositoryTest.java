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

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AirportRepositoryTest {

    private final AirportRepository underTheTest;

    @Autowired
    public AirportRepositoryTest(AirportRepository underTheTest) {
        this.underTheTest = underTheTest;
    }

    @Test
    public void testThatAirportCanBeCreatedAndRecalled() {
        AirportEntity airportEntity = TestDataUtil.createTestAirportEntityA();
        underTheTest.save(airportEntity);
        Optional<AirportEntity> result = underTheTest.findById(airportEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(airportEntity);

    }

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

    @Test
    public void testThatAirportCanBeDeleted() {
        AirportEntity airportEntity = TestDataUtil.createTestAirportEntityA();
        underTheTest.save(airportEntity);
        underTheTest.deleteById(airportEntity.getId());
        Optional<AirportEntity> result = underTheTest.findById(airportEntity.getId());
        assertThat(result).isEmpty();
    }

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