package by.speedteam.speedwagon.controllers;

import by.speedteam.speedwagon.models.Reservation;
import by.speedteam.speedwagon.payload.requests.reservations.CreateReservationRequest;
import by.speedteam.speedwagon.payload.responses.ErrorResponse;
import by.speedteam.speedwagon.payload.responses.SuccessResponse;
import by.speedteam.speedwagon.services.ReservationService;
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
@Tag(name = "Reservation Management", description = "Endpoints for managing reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/api/v1/reservations")
    @Operation(summary = "Get all reservations", description = "Retrieve a list of all reservations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservations retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> getAllReservations() {
        try {
            List<Reservation> reservations = reservationService.getAllReservations();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Reservations retrieved successfully", reservations));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @GetMapping("/api/v1/reservations/{id}")
    @Operation(summary = "Get reservation by ID", description = "Retrieve a reservation by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Reservation not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> getReservationById(@PathVariable long id) {
        try {
            Reservation reservation = reservationService.getReservationById(id)
                    .orElseThrow(() -> new RuntimeException("Reservation not found"));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Reservation retrieved successfully", reservation));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @GetMapping("/api/v1/reservations/user/{userId}")
    @Operation(summary = "Get reservations by user ID", description = "Retrieve reservations for a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User reservations retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "No reservations found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> getReservationsByUserId(@PathVariable("userId") long userId) {
        List<Reservation> reservations = reservationService.getReservationsByUserId(userId);
        if (reservations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("No reservations found"));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>("User reservations retrieved successfully", reservations));
    }

    @PostMapping("/api/v1/reservations")
    @Operation(summary = "Create reservation", description = "Create a new reservation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservation created successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> createReservation(@RequestBody CreateReservationRequest createReservationRequest) {
        try {
            Reservation newReservation = reservationService.createReservation(createReservationRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse<>("Reservation created successfully", newReservation));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @PutMapping("/api/v1/reservations/{id}")
    @Operation(summary = "Update reservation", description = "Update an existing reservation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Reservation not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> updateReservation(@PathVariable long id, @RequestBody Reservation reservation) {
        try {
            Reservation updatedReservation = reservationService.updateReservation(id, reservation);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Reservation updated successfully", updatedReservation));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @DeleteMapping("/api/v1/reservations/{id}")
    @Operation(summary = "Delete reservation", description = "Delete a reservation by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation deleted successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Reservation not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> deleteReservation(@PathVariable long id) {
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Reservation deleted successfully", null));
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
