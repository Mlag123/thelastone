package org.mlagdevelopment.messenger.thelostone.client;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.mlagdevelopment.messenger.thelostone.client.service.RoomClampService;
import org.mlagdevelopment.messenger.thelostone.client.service.RoomClientRequest;

import java.util.Scanner;


public class ClientMain {
    public static Logger log = LogManager.getLogger();

    //TEST DEBUG CODE!!
    public static void main(String[] args) {


        //log.info(new UserAuthentication().login(new LoginRequest("lag123","pophop")).getBody());
        log.info("HELLO WORLD!");


        String secondtoken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5IiwidXNlcm5hbWUiOiJsYWcxMjMiLCJpYXQiOjE3ODkzMjMxMjEsImV4cCI6MTc4OTQwOTUyMX0.m9BQ6SSErx8eFsg6yUhInhvHV59aVhSjw0tGGQNeRss";
        RoomClampService roomControllerClient = new RoomClampService();
        RoomClientRequest clientRequest = new RoomClientRequest();
        roomControllerClient.connect(RoomClampService.TEST_TOKEN);
        roomControllerClient.subscribeToRoom(4L);

        Scanner scanner = new Scanner(System.in);


        RoomClampService second = new RoomClampService();
//        clientRequest.joinRoom(4L);
        second.connect(secondtoken);

        second.subscribeToRoom(4L);


        while (true) {
            String word = scanner.nextLine();
            roomControllerClient.sendMessage(word);
            String secondWord = scanner.nextLine();
            second.sendMessage(secondWord);
        }
    }
}
