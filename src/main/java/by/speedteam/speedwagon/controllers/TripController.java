package by.speedteam.speedwagon.controllers;

import by.speedteam.speedwagon.models.Trip;
import by.speedteam.speedwagon.payload.responses.ErrorResponse;
import by.speedteam.speedwagon.payload.responses.SuccessResponse;
import by.speedteam.speedwagon.services.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Trip Management", description = "Endpoints for managing trips")
public class TripController {
    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/api/v1/trips")
    @Operation(summary = "Get all trips", description = "Retrieve a list of all trips.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trips retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> getAllTrips() {
        try {
            List<Trip> trips = tripService.getAllTrips();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Trips retrieved successfully", trips));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @GetMapping("/api/v1/trips/{id}")
    @Operation(summary = "Get trip by ID", description = "Retrieve a trip by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trip retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Trip not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> getTripById(@PathVariable long id) {
        try {
            Trip trip = tripService.getTripById(id)
                    .orElseThrow(() -> new RuntimeException("Trip not found"));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Trip retrieved successfully", trip));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @GetMapping("/api/v1/trips/search")
    @Operation(summary = "Search trips", description = "Search trips by specific parameters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trips retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> searchTrips(@RequestParam(required = false) Double cost) {
        try {
            List<Trip> trips = tripService.getTripsByCost(cost);
            return ResponseEntity.ok(new SuccessResponse<>("Trips retrieved successfully", trips));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @PostMapping("/api/v1/trips")
    @Operation(summary = "Create trip", description = "Create a new trip.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trip created successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> createTrip(@RequestBody Trip trip) {
        try {
            Trip newTrip = tripService.createTrip(trip);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse<>("Trip created successfully", newTrip));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @PutMapping("/api/v1/trips/{id}")
    @Operation(summary = "Update trip", description = "Update the details of an existing trip.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trip updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid trip details", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Trip not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> updateTrip(@PathVariable long id, @RequestBody Trip trip) {
        try {
            Trip updatedTrip = tripService.updateTrip(id, trip);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Trip updated successfully", updatedTrip));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @DeleteMapping("/api/v1/trips/{id}")
    @Operation(summary = "Delete trip", description = "Delete a trip by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trip deleted successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Trip not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> deleteTrip(@PathVariable long id) {
        try {
            tripService.deleteTrip(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Trip deleted successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }
}
