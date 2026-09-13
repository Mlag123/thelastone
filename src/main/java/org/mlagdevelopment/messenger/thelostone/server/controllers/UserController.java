package org.mlagdevelopment.messenger.thelostone.server.controllers;


import org.apache.tomcat.util.http.parser.Authorization;
import org.mlagdevelopment.messenger.thelostone.server.domain.User;
import org.mlagdevelopment.messenger.thelostone.server.dto.response.UserResponse;
import org.mlagdevelopment.messenger.thelostone.server.repository.UserRepository;
import org.mlagdevelopment.messenger.thelostone.server.service.JwtService;
import org.mlagdevelopment.messenger.thelostone.server.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final JwtService jwtService;
    private final UserRepository repository;
    private final UserService service;
    public UserController(JwtService jwtService, UserRepository repository, UserService service) {
        this.jwtService = jwtService;
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/renameUI")
    public ResponseEntity<String> renameUserUiName() {
        return null;
    }


    @PostMapping("/getUser")
    public ResponseEntity<UserResponse> getUserFromToken(@RequestHeader(AUTHORIZATION) String authHeader) {
        Long userId = extractUserId(authHeader);
        UserResponse response = service.getUserById(userId);

        return ResponseEntity.ok(response);

    }

    private Long extractUserId(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        if (!jwtService.isValid(token)) {
            throw new IllegalArgumentException("Invalid token");
        }
        return jwtService.extractUserId(token);
    }

}
