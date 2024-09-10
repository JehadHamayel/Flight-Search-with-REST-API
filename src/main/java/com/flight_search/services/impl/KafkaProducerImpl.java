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

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerImpl implements KafkaProducer {

     private final KafkaTemplate<String, FlightDto> kafkaTemplate;

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
