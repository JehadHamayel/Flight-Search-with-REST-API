package com.flight_search.controllers;

import com.flight_search.domain.dto.FlightDto;
import com.flight_search.services.impl.KafkaProducerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for sending messages to Kafka topics.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final KafkaProducerImpl kafkaJsonProducer;

    /**
     * Sends a JSON message to a Kafka topic.
     *
     * @param flight the flight information to send. Must not be null.
     * @return a {@link ResponseEntity} with a success message.
     */
    @PostMapping
    public ResponseEntity<String> sendJsonMessage(
            @RequestBody FlightDto flight
    ) {
        // Send the flight message to Kafka
        kafkaJsonProducer.sendMessage(flight);

        // Return a success message
        return ResponseEntity.ok("Message sent to Kafka Topic Successfully");
    }
}
