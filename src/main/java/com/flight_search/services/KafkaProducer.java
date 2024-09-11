package com.flight_search.services;

import com.flight_search.domain.dto.FlightDto;

/**
 * Service interface for producing and sending messages to Kafka topics.
 */
public interface KafkaProducer {

    /**
     * Sends a flight message to a Kafka topic.
     * @param flight the flight data to be sent to the Kafka topic.
     */
    void sendMessage(FlightDto flight);

}
