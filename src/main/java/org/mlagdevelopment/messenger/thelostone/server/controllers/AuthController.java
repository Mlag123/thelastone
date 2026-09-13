package org.mlagdevelopment.messenger.thelostone.server.controllers;


import jakarta.validation.Valid;
import org.mlagdevelopment.messenger.thelostone.server.dto.request.LoginRequest;
import org.mlagdevelopment.messenger.thelostone.server.dto.response.LoginResponse;
import org.mlagdevelopment.messenger.thelostone.server.dto.request.RegisterRequest;
import org.mlagdevelopment.messenger.thelostone.server.dto.response.UserResponse;
import org.mlagdevelopment.messenger.thelostone.server.service.JwtService;
import org.mlagdevelopment.messenger.thelostone.server.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){
        UserResponse response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
        UserResponse userResponse = userService.authenticate(request);
        String token = jwtService.generateToken(userResponse.id(),userResponse.username());
        return ResponseEntity.ok(new LoginResponse(token,userResponse));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        HttpStatus status = ex.getMessage().contains("credentials")
                ? HttpStatus.UNAUTHORIZED
                : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(Map.of("error", ex.getMessage()));
    }


}
