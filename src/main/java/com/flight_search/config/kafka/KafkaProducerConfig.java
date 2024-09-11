package com.flight_search.config.kafka;

import com.flight_search.domain.dto.FlightDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class responsible for setting up Kafka producers.
 * It configures the Kafka producer to send messages to Kafka topics and
 * serializes the message data (in this case, {@link FlightDto} objects) as JSON.
 */
@Configuration
public class KafkaProducerConfig {

    /**
     * The address of the Kafka broker. This is where the producer connects to.
     */
    private String bootstrapAddress = "localhost:9092";

    /**
     * Provides a configuration map containing Kafka producer properties such as
     * the bootstrap servers and serialization mechanisms for keys and values.
     *
     * @return a map of producer configuration properties.
     */
    public Map<String, Object> producerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);  // Kafka broker address
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);  // Key serializer
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);  // Value serializer (FlightDto as JSON)
        return props;
    }

    /**
     * Defines the {@link ProducerFactory} that is responsible for creating Kafka producer instances.
     * It uses the producer configuration properties to establish the connection with the Kafka broker.
     *
     * @return a {@link ProducerFactory} for producing Kafka messages.
     */
    @Bean
    public ProducerFactory<String, FlightDto> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    /**
     * Defines the {@link KafkaTemplate} which is responsible for sending messages to Kafka topics.
     * The KafkaTemplate provides methods for producing messages with serialized data (e.g., {@link FlightDto} objects).
     *
     * @param producerFactory the {@link ProducerFactory} used by the KafkaTemplate.
     * @return a {@link KafkaTemplate} for sending messages to Kafka.
     */
    @Bean
    public KafkaTemplate<String, FlightDto> kafkaTemplate(
            ProducerFactory<String, FlightDto> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }
}
