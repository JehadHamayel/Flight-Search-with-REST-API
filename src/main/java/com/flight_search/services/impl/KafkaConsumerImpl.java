package com.flight_search.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight_search.domain.dto.FlightDto;
import com.flight_search.services.KafkaConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerImpl implements KafkaConsumer {

    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "jehadstopic", groupId = "consumer_flight-search")
    public void listen(String message) throws JsonProcessingException {
        log.warn("Received Message: " + message);
        FlightDto flight = objectMapper.readValue(message, FlightDto.class);
        log.info("Received Message: flightId: " + flight.getId() + " from: " + flight.getDepartureAirport().getCity() + " to: " + flight.getArrivalAirport().getCity());
    }
}
