package by.speedteam.speedwagon.controllers;

import by.speedteam.speedwagon.models.Route;
import by.speedteam.speedwagon.payload.responses.ErrorResponse;
import by.speedteam.speedwagon.payload.responses.SuccessResponse;
import by.speedteam.speedwagon.services.RouteService;
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
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Route Management", description = "Endpoints for managing routes")
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/api/v1/routes")
    @Operation(summary = "Get all routes", description = "Retrieve a list of all routes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Routes retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> getAllRoutes() {
        try {
            List<Route> routes = routeService.getAllRoutes();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Routes retrieved successfully", routes));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @GetMapping("/api/v1/routes/{id}")
    @Operation(summary = "Get route by ID", description = "Retrieve a route by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Route retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Route not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> getRouteById(@PathVariable long id) {
        try {
            Optional<Route> route = routeService.getRouteById(id);
            if (route.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Route not found"));
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Route retrieved successfully", route.get()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @PostMapping("/api/v1/routes")
    @Operation(summary = "Create route", description = "Create a new route.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Route created successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> createRoute(@RequestBody Route route) {
        try {
            Route createdRoute = routeService.createRoute(route);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse<>("Route created successfully", createdRoute));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @PutMapping("/api/v1/routes/{id}")
    @Operation(summary = "Update route", description = "Update an existing route by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Route updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Route not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> updateRoute(@PathVariable long id, @RequestBody Route updatedRoute) {
        try {
            Route route = routeService.updateRoute(id, updatedRoute);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Route updated successfully", route));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @DeleteMapping("/api/v1/routes/{id}")
    @Operation(summary = "Delete route", description = "Delete a route by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Route deleted successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Route not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> deleteRoute(@PathVariable long id) {
        try {
            routeService.deleteRoute(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Route deleted successfully", id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }
}
