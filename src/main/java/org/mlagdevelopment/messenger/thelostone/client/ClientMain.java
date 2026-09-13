package org.mlagdevelopment.messenger.thelostone.client;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.mlagdevelopment.messenger.thelostone.client.service.RoomControllerClient;


public class ClientMain {
    public static Logger log = LogManager.getLogger();

    public static void main(String[] args) {

        log.info("HELLO WORLD!");

        RoomControllerClient roomControllerClient = new RoomControllerClient();

            roomControllerClient.connectToWebSocketRoom(RoomControllerClient.TEST_TOKEN,4L);
    }
}
