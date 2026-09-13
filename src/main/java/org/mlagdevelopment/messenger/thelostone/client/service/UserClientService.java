package org.mlagdevelopment.messenger.thelostone.client.service;

import org.mlagdevelopment.messenger.thelostone.server.domain.User;
import org.mlagdevelopment.messenger.thelostone.server.dto.response.UserResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class UserClientService {

    private final RestClient client;

    private String URL = "http://localhost:8080/api/user";
    public UserClientService() {
        this.client = RestClient.create();
    }



    public ResponseEntity<UserResponse> getUserIdFromToken(String token){

        return client.post()
                .uri(URL+"/getUser")
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION,"Bearer "+token)
                .retrieve()
                .toEntity(UserResponse.class);
    }
}
