package by.speedteam.speedwagon.controllers;

import by.speedteam.speedwagon.models.User;
import by.speedteam.speedwagon.payload.responses.ErrorResponse;
import by.speedteam.speedwagon.payload.responses.SuccessResponse;
import by.speedteam.speedwagon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/v1/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Users retrieved successfully", users));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @GetMapping("/api/v1/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        try {
            User user = userService.getUserById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));
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

//    @PostMapping("/users")
//    public ResponseEntity<?> createUser(@RequestBody User user) {
//        try {
//            User newUser = userService.createUser(user);
//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .body(new SuccessResponse<>("User created successfully", newUser));
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ErrorResponse("Internal server error"));
//        }
//    }

    @PutMapping("/api/v1/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("User updated successfully", updatedUser));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @DeleteMapping("/api/v1/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("User deleted successfully", null));
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
