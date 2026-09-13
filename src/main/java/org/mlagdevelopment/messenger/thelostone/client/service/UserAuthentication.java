package org.mlagdevelopment.messenger.thelostone.client.service;

import com.google.gson.Gson;
import org.mlagdevelopment.messenger.thelostone.server.dto.request.LoginRequest;
import org.mlagdevelopment.messenger.thelostone.server.dto.request.RegisterRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

public class UserAuthentication {

    private final RestClient client;
    private final Gson gson;
    private String REGISTER_URL = "http://127.0.0.1:8080/api/auth/register";
    private String LOGIN_URL = "http://127.0.0.1:8080/api/auth/login";

    public UserAuthentication() {
        this.client = RestClient.create();
        this.gson = new Gson();
    }

    public ResponseEntity<String> register(RegisterRequest request){
         return client.post()
                .uri(REGISTER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(gson.toJson(request))
                .retrieve()
                .toEntity(String.class);


    }
    public ResponseEntity<String> login(LoginRequest request){

        return client.post()
                .uri(LOGIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(gson.toJson(request))
                .retrieve()
                .toEntity(String.class);

    }


}
