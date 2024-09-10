package com.flight_search.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development of Flight Search System");

        Contact myContact = new Contact();
        myContact.setName("Jehad Hamayel");
        myContact.setEmail("jehadhamayel95@gmail.com");

        Info information = new Info()
                .title("Flight Search System API")
                .version("1.0")
                .description("This API exposes endpoints to manage Flight Search.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
