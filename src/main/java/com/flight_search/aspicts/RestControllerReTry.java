package com.flight_search.aspicts;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RestControllerReTry {
    private static int WAIT_MILLIS_BETWEEN_TRIES = 1000;

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object invoke(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        System.out.println(getClass().getSimpleName() + " -> " + thisJoinPoint);

        for (int i = 1;; i++) {
            try {
                return thisJoinPoint.proceed();
            }
            catch (Exception e) {
                System.out.println("  Throttled during try #" + i);
                Thread.sleep(WAIT_MILLIS_BETWEEN_TRIES);
            }
        }
    }

}

//Example of other pointcuts and advices

//    @After("execution(* listFlights())")
//    public void afterFlightController() {
//        System.out.println("After FlightController.createUpdateFlight");
//    }

//    @Before("execution(* listFlights())")
//    public void beforeFlightController() {
//        System.out.println("Before FlightController.createUpdateFlight");
//    }

//    @Before("within(com.flight_search.controllers.FlightController)")
//    public void beforeFlightControllerWithin() {
//        System.out.println("Before FlightController within FlightController");
//    }

//    @After("@within(org.springframework.web.bind.annotation.RestController)")
//    public void afterRestController() {
//        System.out.println("After RestController");
//    }

//    @Before("@annotation(org.springframework.web.bind.annotation.GetMapping)")
//    public void beforeGetMapping() {
//        System.out.println("Before GetMapping");
//    }

//    @Before("target(com.flight_search.repositories.FlightRepository)")
//    public void beforeFlightRepository() {
//        System.out.println("Before FlightRepository");
//    }

//    @After("execution(* listFlights())" +
//            "&& @annotation(org.springframework.web.bind.annotation.GetMapping)")
//    public void afterFlightControllerAndGetMapping() {
//        System.out.println("After FlightController.createUpdateFlight and GetMapping");
//    }

//    @Before("execution(* listFlights())" +
//            "|| @annotation(org.springframework.web.bind.annotation.GetMapping)")
//    public void beforeFlightControllerOrGetMapping() {
//        System.out.println("Before FlightController.createUpdateFlight or GetMapping");
//    }

//    @Pointcut("execution(* listFlights())")
//    public void listFlights() {
//        // Method is empty as this is just a Pointcut, the implementations are in the advices.
//    }

//    @Before("listFlights()")
//    public void beforeListFlights() {
//        System.out.println("Before listFlights using Pointcut");
//    }

//    @Around("listFlights()")
//    public void aroundListFlights(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        System.out.println("1-Around listFlights using Pointcut");
//        proceedingJoinPoint.proceed();
//        System.out.println("2-Around listFlights using Pointcut");
//    }