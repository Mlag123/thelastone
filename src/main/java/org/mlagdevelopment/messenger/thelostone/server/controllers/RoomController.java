package org.mlagdevelopment.messenger.thelostone.server.controllers;


import jakarta.validation.Valid;
import org.mlagdevelopment.messenger.thelostone.server.dto.CreateRoomRequest;
import org.mlagdevelopment.messenger.thelostone.server.dto.RoomResponse;
import org.mlagdevelopment.messenger.thelostone.server.service.JwtService;
import org.mlagdevelopment.messenger.thelostone.server.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {


    private final RoomService roomService;
    private final JwtService jwtService;


    public RoomController(RoomService roomService, JwtService jwtService) {
        this.roomService = roomService;
        this.jwtService = jwtService;
    }


    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(
            @RequestHeader("Authorization") String authHeader, @Valid @RequestBody CreateRoomRequest request) {
        Long userId = extractUserId(authHeader);
        RoomResponse response = roomService.createRoom(userId,request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<List<RoomResponse>> getUserRooms(@RequestHeader("Authorization") String authHeader){
        Long userId = extractUserId(authHeader);
        return ResponseEntity.ok(roomService.getUserRooms(userId));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomResponse> getRoom(@PathVariable Long userId){
        return ResponseEntity.ok(roomService.getRoom(userId));
    }


    @PostMapping("/{roomId}/join")
    public ResponseEntity<RoomResponse> joinRoom(@PathVariable Long roomId,@RequestHeader("Authorization") String authHeader){
        Long userId = extractUserId(authHeader);
        return ResponseEntity.ok(roomService.joinRoom(roomId,userId));
    }

    @PostMapping("/{roomId}/leave")
    public ResponseEntity<RoomResponse> exitRoom(@PathVariable Long roomId, @RequestHeader("Authorization") String authHeader){
         Long userId = extractUserId(authHeader);
         roomService.leaveRoom(roomId,userId);
         return ResponseEntity.noContent().build();
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
