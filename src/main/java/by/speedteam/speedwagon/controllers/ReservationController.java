package by.speedteam.speedwagon.controllers;

import by.speedteam.speedwagon.models.Reservation;
import by.speedteam.speedwagon.payload.requests.reservations.CreateReservationRequest;
import by.speedteam.speedwagon.payload.responses.ErrorResponse;
import by.speedteam.speedwagon.payload.responses.SuccessResponse;
import by.speedteam.speedwagon.services.ReservationService;
import by.speedteam.speedwagon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {
    private final ReservationService reservationService;
    private final UserService userService;

    @Autowired
    public ReservationController(ReservationService reservationService, UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @GetMapping("/api/v1/reservations")
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
