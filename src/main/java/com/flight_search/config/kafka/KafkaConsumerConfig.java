package com.flight_search.config.kafka;

import com.flight_search.domain.dto.FlightDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class responsible for setting up Kafka consumers.
 * It configures the Kafka consumer to listen to messages from a Kafka topic and
 * deserializes the incoming message data into {@link FlightDto} objects.
 */
@Configuration
public class KafkaConsumerConfig {

    /**
     * The address of the Kafka broker. This is where the consumer connects to.
     */
    private String bootstrapAddress = "localhost:9092";

    /**
     * Provides a configuration map containing Kafka consumer properties such as
     * the bootstrap servers, group ID, and deserialization mechanisms for keys and values.
     *
     * @return a map of consumer configuration properties.
     */
    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);  // Kafka broker address
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "flight_search");  // Consumer group ID
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");  // Start reading from earliest offset
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  // Key deserializer
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);  // Value deserializer
        return props;
    }

    /**
     * Defines the {@link ConsumerFactory} that is responsible for creating Kafka consumer instances.
     * It uses the consumer configuration properties to establish the connection with the Kafka broker.
     *
     * @return a {@link ConsumerFactory} for deserializing Kafka messages.
     */
    @Bean
    public ConsumerFactory<String, FlightDto> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    /**
     * Defines the {@link KafkaListenerContainerFactory} which is responsible for managing
     * Kafka listeners. It creates and configures containers for Kafka message listeners
     * that listen for messages from Kafka topics.
     *
     * @param consumerFactory the {@link ConsumerFactory} to be used by the container factory.
     * @return a {@link KafkaListenerContainerFactory} for handling Kafka message listeners.
     */
    @Bean
    public KafkaListenerContainerFactory<
            ConcurrentMessageListenerContainer<String, FlightDto>> factory(
            ConsumerFactory<String, FlightDto> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, FlightDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);  // Assign the consumer factory
        return factory;
    }

}
