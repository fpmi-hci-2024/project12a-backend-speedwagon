package by.speedteam.speedwagon.controllers;

import by.speedteam.speedwagon.models.Trip;
import by.speedteam.speedwagon.payload.responses.ErrorResponse;
import by.speedteam.speedwagon.payload.responses.SuccessResponse;
import by.speedteam.speedwagon.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TripController {
    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/api/v1/trips")
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
