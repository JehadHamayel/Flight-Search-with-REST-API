package com.flight_search.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class responsible for setting up OpenAPI documentation for the Flight Search System API.
 * This class defines the API's metadata, contact information, and the server details for Swagger UI.
 */
@Configuration
public class OpenAPIConfiguration {

    /**
     * Defines the OpenAPI specification for the Flight Search System.
     * It includes API metadata such as title, version, description, and contact information,
     * as well as the server details where the API is hosted.
     *
     * @return an {@link OpenAPI} object containing the API's metadata and server details.
     */
    @Bean
    public OpenAPI defineOpenApi() {
        // Define the server where the API is available
        Server server = new Server();
        server.setUrl("http://localhost:8080");  // API server URL
        server.setDescription("Development of Flight Search System");  // Description of the server environment

        // Define contact information for the API
        Contact myContact = new Contact();
        myContact.setName("Jehad Hamayel");  // Contact name
        myContact.setEmail("jehadhamayel95@gmail.com");  // Contact email

        // Define API information (title, version, description, and contact)
        Info information = new Info()
                .title("Flight Search System API")  // API title
                .version("1.0")  // API version
                .description("This API exposes endpoints to manage Flight Search.")  // API description
                .contact(myContact);  // Contact information

        // Return the OpenAPI specification
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
