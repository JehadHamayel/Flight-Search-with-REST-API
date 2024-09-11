package com.flight_search.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configuration class responsible for creating Kafka topics.
 * It defines the Kafka topic(s) used in the application for message publishing and consumption.
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * Defines a new Kafka topic named "jehadstopic".
     * The topic is created if it doesn't already exist.
     *
     * @return a {@link NewTopic} object representing the Kafka topic.
     */
    @Bean
    public NewTopic jehadscodeTopic() {
        return TopicBuilder
                .name("jehadstopic")  // Define the topic name
                .build();
    }
}
