package org.mlagdevelopment.messenger.thelostone;

import com.google.gson.Gson;
import jdk.jfr.ContentType;
import org.mlagdevelopment.messenger.thelostone.server.dto.LoginRequest;
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

    private String register_url = "http://127.0.0.1:8080/api/auth/register";
    private String login_url ="http://127.0.0.1:8080/api/auth/login";

    public void test(){
        RestClient _default = RestClient.create();




    /*    ResponseEntity<String> result = _default.post()
                .uri(register_url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Gson().toJson(new RegisterRequest("TESTMLAG","mamama","pophop")))
                .retrieve()
                .toEntity(String.class);

        System.out.println(result.getBody());*/

        ResponseEntity<String> _result = _default.post()
                .uri(login_url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Gson().toJson(new LoginRequest("TESTMLAG","pophop")))
                .retrieve()
                .toEntity(String.class);

        System.out.println(_result.getBody());



    }

    public static void main(String[] args) {
        new AuthTest().test();
    }




}
