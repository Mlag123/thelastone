package org.mlagdevelopment.messenger.thelostone.client;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.mlagdevelopment.messenger.thelostone.client.service.RoomControllerClient;

import java.util.Scanner;


public class ClientMain {
    public static Logger log = LogManager.getLogger();

    public static void main(String[] args) {

        log.info("HELLO WORLD!");

        RoomControllerClient roomControllerClient = new RoomControllerClient();

        roomControllerClient.connect(RoomControllerClient.TEST_TOKEN);
        roomControllerClient.subscribeToRoom(4L);

        Scanner scanner = new Scanner(System.in);
        while (true){
            String word = scanner.nextLine();
            roomControllerClient.sendMessage(word);
        }
    }
}
