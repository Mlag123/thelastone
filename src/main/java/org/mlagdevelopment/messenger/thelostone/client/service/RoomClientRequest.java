package org.mlagdevelopment.messenger.thelostone.client.service;

import com.google.gson.Gson;
import org.mlagdevelopment.messenger.thelostone.server.dto.response.JoinRoomResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class RoomClientRequest {

    private final RestClient client;

    private final Gson gson;

    private String ROOT_ROOM_URL = "http://127.0.0.1:8080/api/rooms";

    public RoomClientRequest() {
        this.client = RestClient.create();
        this.gson = new Gson();
    }


    public ResponseEntity<Void> joinRoom(Long roomId, String token){
        return client.post()
                .uri("/api/rooms/"+roomId.toString()+"/join")
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION,"Bearer "+token)
                .retrieve()
                .toBodilessEntity();
    }

}
