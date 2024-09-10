package com.flight_search.services;

import com.flight_search.domain.dto.FlightDto;

public interface KafkaProducer {

    void sendMessage(FlightDto flight);

}
