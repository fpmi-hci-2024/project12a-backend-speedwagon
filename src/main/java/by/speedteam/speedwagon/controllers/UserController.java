package by.speedteam.speedwagon.controllers;

import by.speedteam.speedwagon.models.User;
import by.speedteam.speedwagon.payload.requests.users.UpdateUserRequest;
import by.speedteam.speedwagon.payload.responses.ErrorResponse;
import by.speedteam.speedwagon.payload.responses.SuccessResponse;
import by.speedteam.speedwagon.payload.responses.users.UserDto;
import by.speedteam.speedwagon.services.UserService;
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
@Tag(name = "User Management", description = "Endpoints for managing users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/v1/users")
    @Operation(summary = "Get all users", description = "Retrieve a list of all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserDto> users = userService.getAllUsers();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Users retrieved successfully", users));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @GetMapping("/api/v1/users/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        try {
            UserDto user = userService.getUserDtoById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("User retrieved successfully", user));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @PutMapping("/api/v1/users/{id}")
    @Operation(summary = "Update user", description = "Update the details of an existing user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Email or phone already exists", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody UpdateUserRequest updateUserRequest) {
        Optional<User> userData = userService.getUserById(id);
        if (userData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("User not found"));
        }
        if (userService.isEmailAlreadyExists(updateUserRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Email already exists"));
        }
        if (userService.isPhoneAlreadyExists(updateUserRequest.getPhone())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Phone already exists"));
        }
        try {
            UserDto updatedUser = userService.updateUser(id, updateUserRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("User updated successfully", updatedUser));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @DeleteMapping("/api/v1/users/{id}")
    @Operation(summary = "Delete user", description = "Delete a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        Optional<User> userData = userService.getUserById(id);
        if (userData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("User not found"));
        }
        try {
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("User deleted successfully", id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }
}
