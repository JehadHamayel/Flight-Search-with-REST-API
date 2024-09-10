package com.flight_search.controllers;

import com.flight_search.domain.dto.FlightDto;
import com.flight_search.services.impl.KafkaProducerImpl;
//import com.flight_search.services.impl.producer.KafkaProducerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final KafkaProducerImpl kafkaJsonProducer;

    @PostMapping
    public ResponseEntity<String> sendJsonMessage(
            @RequestBody FlightDto flight
    ) {
        kafkaJsonProducer.sendMessage(flight);
        return ResponseEntity.ok("Message sent to Kafka Topic Successfully");
    }
}
