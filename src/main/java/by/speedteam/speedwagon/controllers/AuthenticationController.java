package by.speedteam.speedwagon.controllers;

import by.speedteam.speedwagon.models.User;
import by.speedteam.speedwagon.models.UserDetailsImpl;
import by.speedteam.speedwagon.payload.requests.users.LoginRequest;
import by.speedteam.speedwagon.payload.requests.users.RegisterRequest;
import by.speedteam.speedwagon.payload.responses.ErrorResponse;
import by.speedteam.speedwagon.payload.responses.SuccessResponse;
import by.speedteam.speedwagon.payload.responses.jwt.JwtResponse;
import by.speedteam.speedwagon.payload.responses.users.UserDto;
import by.speedteam.speedwagon.security.JwtUtils;
import by.speedteam.speedwagon.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserService userService,
                                    JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Register a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Email already exists", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userService.isEmailAlreadyExists(registerRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Email already exists"));
        }
        try {
            UserDto newUser = userService.registerUser(registerRequest);

            long newUserId = newUser.getId();
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse<>("User registered successfully", newUserId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticates the user and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Optional<User> userData = userService.getUserById(userDetails.getId());
        if (userData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Bad credentials"));
        }

        JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>("Login successful", jwtResponse));
    }
}
