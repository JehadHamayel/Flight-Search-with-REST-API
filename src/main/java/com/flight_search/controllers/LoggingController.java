package com.flight_search.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to demonstrate various logging levels using SLF4J.
 */
@RestController
@Slf4j
public class LoggingController {

    /**
     * Handles requests to the root URL ("/").
     * Logs messages at different levels to demonstrate SLF4J logging.
     *
     * @return a simple string message.
     */
    @RequestMapping("/")
    public String index() {
        // Log messages at various levels
        log.trace("A TRACE Message");  // TRACE level - very detailed logs, useful for debugging
        log.debug("A DEBUG Message");  // DEBUG level - less detailed than TRACE, used for debugging
        log.info("An INFO Message");   // INFO level - general information about the application's progress
        log.warn("A WARN Message");    // WARN level - indicates a potential problem
        log.error("An ERROR Message"); // ERROR level - indicates an error that has occurred

        // Return a message to the client
        return "Howdy! Check out the Logs to see the output...";
    }
}
