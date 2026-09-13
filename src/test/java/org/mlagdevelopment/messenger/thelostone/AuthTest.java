package org.mlagdevelopment.messenger.thelostone;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.mlagdevelopment.messenger.thelostone.client.service.UserAuthentication;
import org.mlagdevelopment.messenger.thelostone.server.dto.request.LoginRequest;
import org.mlagdevelopment.messenger.thelostone.server.dto.response.LoginResponse;

public class AuthTest {
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(java.time.Instant.class,
                    (com.google.gson.JsonDeserializer<java.time.Instant>)
                            (json, type, ctx) -> java.time.Instant.parse(json.getAsString()))
            .create();
    private String register_url = "http://127.0.0.1:8080/api/auth/register";
    private String login_url = "http://127.0.0.1:8080/api/auth/login";
    private UserAuthentication authentication = new UserAuthentication();

    public String test() {
        System.out.println("Starting auth test");
        LoginRequest loginRequest = new LoginRequest("TESTMLAG", "pophop");
        System.out.println("Login: " + loginRequest.username() + " pass: " + loginRequest.password());

        int code = authentication.login(loginRequest).getStatusCode().value();
        if (code == 200) {
            String json_put = authentication.login(loginRequest).getBody();
            LoginResponse loginResponse = gson.fromJson(json_put, LoginResponse.class);
            System.out.println("token: " + loginResponse.token());
            System.out.println("PASS!");
            return loginResponse.token();
        } else {
            System.out.println("Error code: " + code + " FAILED");
        }

        return null;
    }

    public static void main(String[] args) {
        new AuthTest().test();
    }


}
