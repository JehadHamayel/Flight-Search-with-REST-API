package com.flight_search;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * The entry point of the Flight Search application.
 * <p>
 * This class is annotated with {@link SpringBootApplication} to enable auto-configuration, component scanning,
 * and configuration. It also enables scheduling with {@link EnableScheduling} and provides a scheduled task.
 * </p>
 * <p>
 * The class uses {@link Slf4j} for logging and {@link Getter} from Lombok to generate getters for its fields.
 * </p>
 */
@Getter
@SpringBootApplication
@Slf4j
@EnableScheduling
public class FlightSearchApplication {

	/**
	 * The main method that serves as the entry point for the Spring Boot application.
	 *
	 * @param args Command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(FlightSearchApplication.class, args);
	}

	/**
	 * The port number from the application properties.
	 */
	@Value("${server.port}")
	private String port;

	/**
	 * A scheduled task that logs a message at a fixed rate of 5000 milliseconds.
	 * The message logged includes the server port retrieved from application properties.
	 */
	@Scheduled(fixedRate = 5000)
	public void scheduleTaskWithFixedRate() {
		log.info("scheduled message: " + port);
	}

	/**
	 * A {@link CommandLineRunner} bean for sending messages to a Kafka topic.
	 *
	 * <p>
	 * This bean is commented out. When enabled, it will send 10,000 messages to the Kafka topic "jehadstopic"
	 * on application startup.
	 * </p>
	 *
	 * @param kafkaTemplate The {@link KafkaTemplate} used to send messages to Kafka.
	 * @return A {@link CommandLineRunner} instance that sends messages to Kafka.
	 */
	// @Bean
	// CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate) {
	//     return args -> {
	//         for (int i = 0; i < 10000; i++) {
	//             kafkaTemplate.send("jehadstopic", "Hello Kafka " + i);
	//         }
	//     };
	// }

}
