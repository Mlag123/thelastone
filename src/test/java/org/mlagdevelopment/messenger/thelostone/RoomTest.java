package org.mlagdevelopment.messenger.thelostone;

import org.mlagdevelopment.messenger.thelostone.server.dto.CreateRoomRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

public class RoomTest {


    private RestClient client  = RestClient.create();
    private String url  = "http://localhost:8080/api/rooms";
    private String auth_token;


    public ResponseEntity<String> test(){
        auth_token = new AuthTest().test();

        return client.post()
                .uri(url)
                .header("Authorization","Bearer "+auth_token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CreateRoomRequest("test_ROOM"))
                .retrieve()
                .toEntity(String.class);



    }


    



}
