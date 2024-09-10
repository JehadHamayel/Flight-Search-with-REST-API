package com.flight_search.controllers;

import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoggingController {

//    Logger logger = LoggerFactory.getLogger(LoggingController.class);

    @RequestMapping("/")
    public String index() {
        log.trace("A TRACE Message");
        log.debug("A DEBUG Message");
        log.info("An INFO Message");
        log.warn("A WARN Message");
        log.error("An ERROR Message");


//        logger.trace("A TRACE Message");
//        logger.debug("A DEBUG Message");
//        logger.info("An INFO Message");
//        logger.warn("A WARN Message");
//        logger.error("An ERROR Message");

        return "Howdy! Check out the Logs to see the output...";
    }
}
