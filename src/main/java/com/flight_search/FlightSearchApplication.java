package com.flight_search;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Getter
@SpringBootApplication
@Slf4j
@EnableScheduling
public class FlightSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightSearchApplication.class, args);
	}

	@Value("${server.port}")
    private String message ;

	@Scheduled(fixedRate = 5000)
	public void scheduleTaskWithFixedRate() {
        log.info("scheduled message: " + message);
	}

//	@Bean
//	CommandLineRunner commandLineRunner (KafkaTemplate<String, String> kafkaTemplate){
//		return args -> {
//			for (int i = 0; i < 10000; i++) {
//				kafkaTemplate.send("jehadstopic", "Hello Kafka " + i);
//			}
//		};
//	}

}
