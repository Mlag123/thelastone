package org.mlagdevelopment.messenger.thelostone;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jdk.jfr.ContentType;
import org.mlagdevelopment.messenger.thelostone.client.service.UserAuthentication;
import org.mlagdevelopment.messenger.thelostone.server.dto.LoginRequest;
import org.mlagdevelopment.messenger.thelostone.server.dto.LoginResponse;
import org.mlagdevelopment.messenger.thelostone.server.dto.RegisterRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AuthTest {
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(java.time.Instant.class,
                    (com.google.gson.JsonDeserializer<java.time.Instant>)
                            (json, type, ctx) -> java.time.Instant.parse(json.getAsString()))
            .create();
    private String register_url = "http://127.0.0.1:8080/api/auth/register";
    private String login_url = "http://127.0.0.1:8080/api/auth/login";
    private UserAuthentication authentication = new UserAuthentication();

    public void test() {
        System.out.println("Starting auth test");
        LoginRequest loginRequest = new LoginRequest("TESTMLAG", "pophop");
        System.out.println("Login: " + loginRequest.username() + " pass: " + loginRequest.password());

        int code = authentication.login(loginRequest).getStatusCode().value();
        if (code == 200) {
            String json_put = authentication.login(loginRequest).getBody();
            LoginResponse loginResponse = gson.fromJson(json_put, LoginResponse.class);
            System.out.println("token: " + loginResponse.token());
            System.out.println("PASS!");
        } else {
            System.out.println("Error code: " + code + " FAILED");
        }


    }

    public static void main(String[] args) {
        new AuthTest().test();
    }


}
