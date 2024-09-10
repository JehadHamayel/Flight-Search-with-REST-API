package com.flight_search.controllers;

import com.flight_search.domain.dto.AirportDto;
import com.flight_search.domain.entity.AirportEntity;
import com.flight_search.mappers.Mapper;
import com.flight_search.services.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;
    private final Mapper<AirportEntity, AirportDto> airportMapper;

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Airport created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AirportEntity.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)})
    @Tag(name = "Post", description = "Post methods of APIs")
    @Operation(summary = "Create an airport",
            description = "Create a new airport")
    @PostMapping("/airport")
    public ResponseEntity<AirportDto> createAirport(
            @Parameter( description = "Airport to add. Cannot be null or empty.",
                    required = true )
            @RequestBody AirportDto airportDto
    ) {

        log.warn("No need to add the airport id in the request body. It will be auto-generated");

        AirportEntity airportEntity = airportMapper.mapFrom(airportDto);

        try {
            AirportEntity savedAirportEntity = airportService.save(airportEntity.getId(), airportEntity);
            log.info("Airport with id " + savedAirportEntity.getId() + " created");
            return new ResponseEntity<>(airportMapper.mapTo(savedAirportEntity), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error occurred while creating the airport: ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AirportEntity.class)) })})
    @Tag(name = "Get", description = "Get methods of APIs")
    @Operation(summary = "Get all airports",
            description = "Get all airports")
    @GetMapping("/airport")
    public List<AirportDto> listAirports() {
        log.info("Fetching all airports");
        List<AirportEntity> airports = airportService.findAll();
        log.info("Fetched " + airports.size() + " airports");
        return airports.stream().map(airportMapper::mapTo).collect(Collectors.toList());
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AirportEntity.class)) })})
    @Tag(name = "Get", description = "Get methods of APIs")
    @Operation(summary = "Get all airports with pagination",
            description = "Get all airports with pagination")
    @GetMapping("/airportPages")
    public Page<AirportDto> listAirports(Pageable pageable) {
        log.info("Fetching airports with pagination, page number: " + pageable.getPageNumber());
        Page<AirportEntity> airports = airportService.findAll(pageable);
        log.info("Fetched " + airports.getTotalElements() + " airports in total across pages");
        return airports.map(airportMapper::mapTo);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AirportEntity.class)) }),
            @ApiResponse(responseCode = "404", description = "Airport not found",
                    content = @Content) })
    @Tag(name = "Get", description = "Get methods of APIs")
    @Operation(summary = "Get an airport by id",
            description = "Get an airport by its id")
    @GetMapping("/airport/{id}")
    public ResponseEntity<AirportDto> getAirportById(
            @Parameter(description = "Id of the airport to return. Cannot be empty.",
                    required = true)
            @PathVariable("id") Long id
    ) {

        log.info("Fetching airport with id " + id);
        Optional<AirportEntity> airportEntity = airportService.findById(id);

        return airportEntity.map(airport -> {
                    log.info("Airport with id " + id + " found");
                    return new ResponseEntity<>(airportMapper.mapTo(airport), HttpStatus.OK);
                })
                .orElseGet(() -> {
                    log.warn("Airport with id " + id + " not found");
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AirportEntity.class)) }),
            @ApiResponse(responseCode = "404", description = "Airport not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @Tag(name = "Put", description = "Put methods of APIs")
    @Operation(summary = "Update an airport",
            description = "Update an existing airport by its id")
    @PutMapping("/airport/{id}")
    public ResponseEntity<AirportDto> updateAirport(
            @Parameter(
                    description = "Id of the airport to update and Airport information to update. Cannot be empty.",
                    required = true)
            @PathVariable("id") Long id,
            @RequestBody AirportDto airportDto) {

        log.info("Updating airport with id " + id);

        if (!airportService.isExistingAirport(id)) {
            log.warn("Airport with id " + id + " not found for update");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        airportDto.setId(id);
        AirportEntity airportEntity = airportMapper.mapFrom(airportDto);

        try {
            AirportEntity updatedAirportEntity = airportService.save(id, airportEntity);
            log.info("Airport with id " + id + " updated");
            return new ResponseEntity<>(airportMapper.mapTo(updatedAirportEntity), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while updating the airport with id " + id + ": ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AirportEntity.class)) }),
            @ApiResponse(responseCode = "409", description = "Airport is used in a flight",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @Tag(name = "Delete", description = "Delete methods of APIs")
    @Operation(summary = "Delete an airport",
            description = "Delete an existing airport by its id")
    @DeleteMapping("/airport/{id}")
    public ResponseEntity<Void> deleteAirport(
            @Parameter(description = "Id of the airport to delete. Cannot be empty.",
                    required = true)
            @PathVariable("id") Long id) {

        log.info("Deleting airport with id " + id);

        try {
            airportService.delete(id);
            log.info("Airport with id " + id + " deleted successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataIntegrityViolationException e) {
            log.error("Cannot delete airport with id " + id + " as it is used in a flight", e);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            log.error("Error occurred while deleting the airport with id " + id + ": ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
