package com.flight_search.controllers;

import com.flight_search.domain.dto.FlightDto;
import com.flight_search.domain.entity.FlightEntity;
import com.flight_search.domain.dto.FlightSearchRequest;
import com.flight_search.mappers.impl.FlightMapper;
import com.flight_search.services.AirportService;
import com.flight_search.services.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller for handling flight-related requests.
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class FlightController {

    private final FlightMapper flightMapper;
    private final FlightService flightService;
    private final AirportService airportService;

    /**
     * Creates or updates a flight.
     *
     * @param flightDto the flight data to create or update. Must not be null or empty.
     * @return a {@link ResponseEntity} containing the created or updated {@link FlightDto}.
     */
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flight created or fully updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FlightEntity.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)})
    @Tag(name = "Put", description = "Put methods of APIs")
    @Operation(summary = "Create or update a flight",
            description = "Create a new flight or update an existing one")
    @PutMapping("/flights")
    public ResponseEntity<FlightDto> createUpdateFlight(
            @Parameter(description = "Flight to add or update. Cannot be null or empty.",
                    required = true)
            @RequestBody FlightDto flightDto
    ) {
        log.info("Received request to create or update a flight: {}", flightDto);

        if (!airportService.isExistingAirport(flightDto.getDepartureAirport().getId()) ||
                !airportService.isExistingAirport(flightDto.getArrivalAirport().getId())) {
            log.warn("Invalid airport IDs provided: departureAirportId={}, arrivalAirportId={}",
                    flightDto.getDepartureAirport().getId(), flightDto.getArrivalAirport().getId());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (flightDto.getReturnDateTime() == null) {
            flightDto.setReturnDateTime("");
        }

        FlightEntity flightEntity = flightMapper.mapFrom(flightDto);
        FlightEntity savedFlightEntity = flightService.save(flightEntity);
        log.info("Flight saved with ID: {}", savedFlightEntity.getId());

        FlightDto savedFlightDto = flightMapper.mapTo(savedFlightEntity);
        return new ResponseEntity<>(savedFlightDto, HttpStatus.OK);
    }

    /**
     * Retrieves all flights.
     *
     * @return a list of all {@link FlightDto} objects.
     */
    @ApiResponse(responseCode = "200", description = "Find all flights",
            content = { @Content(mediaType = "application/json") })
    @Tag(name = "Get", description = "Get methods of APIs")
    @Operation(summary = "Get all flights",
            description = "Retrieve a list of all flights")
    @GetMapping("/flights")
    public List<FlightDto> listFlights() {
        log.info("Fetching all flights");
        List<FlightEntity> flights = flightService.findAll();
        return flights.stream().map(flightMapper::mapTo).collect(Collectors.toList());
    }

    /**
     * Retrieves all flights with pagination.
     *
     * @param pageable pagination information.
     * @return a {@link Page} of {@link FlightDto} objects.
     */
    @ApiResponse(responseCode = "200", description = "Flights found as pages",
            content = { @Content(mediaType = "application/json") })
    @Tag(name = "Get", description = "Get methods of Flight APIs")
    @Operation(summary = "Get all flights with pagination",
            description = "Retrieve a paginated list of flights")
    @GetMapping("/flightsPages")
    public Page<FlightDto> listFlights(Pageable pageable) {
        log.info("Fetching flights with pagination, page: {}", pageable.getPageNumber());
        Page<FlightEntity> flights = flightService.findAll(pageable);
        return flights.map(flightMapper::mapTo);
    }

    /**
     * Retrieves flights based on search criteria.
     *
     * @param departureAirportId the ID of the departure airport. Must not be null.
     * @param arrivalAirportId the ID of the arrival airport. Must not be null.
     * @param departureDateTime the departure date and time. Must not be null.
     * @param returnDateTime the return date and time. Optional.
     * @return a list of {@link FlightDto} objects matching the search criteria.
     */
    @ApiResponse(responseCode = "200", description = "Flights found",
            content = { @Content(mediaType = "application/json") })
    @Tag(name = "Get", description = "Get methods of APIs")
    @Operation(summary = "Get flights by search criteria",
            description = "Retrieve flights based on specified search criteria")
    @GetMapping("/listFlights/{departureAirportId}/{arrivalAirportId}/{departureDateTime}/{returnDateTime}")
    public List<FlightDto> listFlightsByFlightsInfo(
            @Parameter(description = "ID of the departure airport. Must not be null.", required = true)
            @PathVariable Long departureAirportId,
            @Parameter(description = "ID of the arrival airport. Must not be null.", required = true)
            @PathVariable Long arrivalAirportId,
            @Parameter(description = "Departure date and time. Must not be null.", required = true)
            @PathVariable String departureDateTime,
            @Parameter(description = "Return date and time. Optional.")
            @PathVariable(required = false) String returnDateTime
    ) {

        FlightSearchRequest flightSearchRequest = FlightSearchRequest.builder()
                .departureAirportId(departureAirportId)
                .arrivalAirportId(arrivalAirportId)
                .departureDateTime(departureDateTime)
                .returnDateTime(returnDateTime)
                .build();

        log.info("Searching for flights with criteria: {}", flightSearchRequest);

        if (flightSearchRequest.getReturnDateTime() == null) {
            flightSearchRequest.setReturnDateTime("");
        }

        List<FlightEntity> suggestedFlights = new ArrayList<>();

        if (!flightSearchRequest.getReturnDateTime().isEmpty()) {
            log.info("Searching for return flights as well");

            Long temp = flightSearchRequest.getDepartureAirportId();
            flightSearchRequest.setDepartureAirportId(flightSearchRequest.getArrivalAirportId());
            flightSearchRequest.setArrivalAirportId(temp);

            flightSearchRequest.setDepartureDateTime(flightSearchRequest.getReturnDateTime());
            flightSearchRequest.setReturnDateTime("");

            List<FlightEntity> flightsToReturn = flightService.findAllByFlightsInfo(flightSearchRequest);
            suggestedFlights.addAll(flightsToReturn);
        }

        return suggestedFlights.stream().map(flightMapper::mapTo).collect(Collectors.toList());
    }

    /**
     * Retrieves a flight by its ID.
     *
     * @param id the ID of the flight to retrieve. Must not be empty.
     * @return a {@link ResponseEntity} containing the {@link FlightDto} if found, or {@link HttpStatus#NOT_FOUND} if not found.
     */
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flight found",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Flight not found",
                    content = @Content)})
    @Tag(name = "Get", description = "Get methods of APIs")
    @Operation(summary = "Get a flight by ID",
            description = "Retrieve a flight by its ID")
    @GetMapping("/flights/{id}")
    public ResponseEntity<FlightDto> getFlightById(
            @Parameter(description = "ID of the flight to return. Cannot be empty.", required = true)
            @PathVariable("id") Long id) {
        log.info("Fetching flight with ID: {}", id);
        Optional<FlightEntity> flightEntity = flightService.findById(id);
        return flightEntity.map(flight -> {
            log.info("Flight found with ID: {}", id);
            return new ResponseEntity<>(flightMapper.mapTo(flight), HttpStatus.OK);
        }).orElseGet(() -> {
            log.warn("Flight not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    /**
     * Partially updates a flight by its ID.
     *
     * @param id the ID of the flight to update. Must not be empty.
     * @param flightDto the flight data to update. Must not be null or empty.
     * @return a {@link ResponseEntity} containing the updated {@link FlightDto} if successful, or {@link HttpStatus#NOT_FOUND} if the flight does not exist.
     */
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flight partially updated",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Flight not found",
                    content = @Content)})
    @Tag(name = "Patch", description = "Patch methods of APIs")
    @Operation(summary = "Partially update a flight",
            description = "Update partial information of an existing flight by its ID")
    @PatchMapping("/flights/{id}")
    public ResponseEntity<FlightDto> partialUpdateFlight(
            @Parameter(description = "ID of the flight to update and flight information to update. Cannot be empty.", required = true)
            @PathVariable("id") Long id,
            @RequestBody FlightDto flightDto
    ) {
        log.info("Received request to partially update flight with ID: {}", id);

        if (!flightService.isExistingFlight(id)) {
            log.warn("Flight not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        FlightEntity flightEntity = flightMapper.mapFrom(flightDto);
        FlightEntity updatedFlightEntity = flightService.partialUpdate(id, flightEntity);
        log.info("Flight partially updated with ID: {}", id);

        FlightDto updatedFlightDto = flightMapper.mapTo(updatedFlightEntity);
        return new ResponseEntity<>(updatedFlightDto, HttpStatus.OK);
    }

    /**
     * Deletes a flight by its ID.
     *
     * @param id the ID of the flight to delete. Must not be empty.
     * @return a {@link ResponseEntity} with {@link HttpStatus#NO_CONTENT} if the deletion is successful.
     */
    @ApiResponse(responseCode = "204", description = "Flight deleted",
            content = { @Content(mediaType = "application/json") })
    @Tag(name = "Delete", description = "Delete methods of APIs")
    @Operation(summary = "Delete a flight",
            description = "Delete an existing flight by its ID")
    @DeleteMapping("/flights/{id}")
    public ResponseEntity<Void> deleteFlight(
            @Parameter(description = "ID of the flight to delete. Cannot be empty.", required = true)
            @PathVariable("id") Long id) {
        log.info("Received request to delete flight with ID: {}", id);
        flightService.delete(id);
        log.info("Flight deleted with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
