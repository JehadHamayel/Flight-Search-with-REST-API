package com.flight_search.services;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface KafkaConsumer {

    void listen(String message) throws JsonProcessingException;

}
