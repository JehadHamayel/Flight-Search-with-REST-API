package com.flight_search.services;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Service interface for consuming messages from Kafka topics.
 */
public interface KafkaConsumer {

    /**
     * Processes the consumed message from Kafka.
     * @param message the message received from the Kafka topic.
     * @throws JsonProcessingException if there is an error processing the message as JSON.
     */
    void listen(String message) throws JsonProcessingException;

}
