package com.flight_search.services.impl;

import com.flight_search.domain.dto.FlightDto;
import com.flight_search.services.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link KafkaProducer} interface.
 * Produces messages to a Kafka topic.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, FlightDto> kafkaTemplate;

    /**
     * Sends a message to the Kafka topic with the provided flight details.
     * @param flight the flight details to be sent as a message.
     */
    @Override
    public void sendMessage(FlightDto flight) {
        Message<FlightDto> message = MessageBuilder
                .withPayload(flight)
                .setHeader(KafkaHeaders.TOPIC, "jehadstopic")
                .build();
        log.info("#### -> Producing message -> {}", flight.toString());
        this.kafkaTemplate.send(message);
    }
}
