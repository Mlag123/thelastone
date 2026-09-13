package org.mlagdevelopment.messenger.thelostone;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.TimeUnit;

public class StompTest {
    public static void main(String[] args) throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI4IiwidXNlcm5hbWUiOiJURVNUTUxBRyIsImlhdCI6MTc4OTI4NzgyOSwiZXhwIjoxNzg5Mzc0MjI5fQ.nzz4HxzvNk6d2sZzJboofqDJpwbQmVBwbv9Lbt7wfjI";

        WebSocketStompClient client = new WebSocketStompClient(new StandardWebSocketClient());
        client.setMessageConverter(new MappingJackson2MessageConverter());

        WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
        StompHeaders connectHeaders = new StompHeaders();
        connectHeaders.add("Authorization", "Bearer " + token);

        System.out.println("Connecting with token...");

        try {
            StompSession session = client
                    .connectAsync("ws://localhost:8080/ws", handshakeHeaders, connectHeaders,
                            new StompSessionHandlerAdapter() {
                                @Override
                                public void handleTransportError(StompSession session, Throwable exception) {
                                    System.out.println("Transport error: " + exception.getMessage());
                                }

                                @Override
                                public void handleException(StompSession session,
                                                            org.springframework.messaging.simp.stomp.StompCommand command,
                                                            StompHeaders headers, byte[] payload, Throwable exception) {
                                    System.out.println("STOMP exception: " + exception.getMessage());
                                }
                            })
                    .get(5, TimeUnit.SECONDS);

            System.out.println("Connected: " + session.isConnected());
            System.out.println("Session ID: " + session.getSessionId());

            session.disconnect();
            System.out.println("Disconnected");

        } catch (Exception e) {
            System.out.println("Failed: " + e.getMessage());
            Throwable cause = e.getCause();
            while (cause != null) {
                System.out.println("  Caused by: " + cause.getMessage());
                cause = cause.getCause();
            }
        }
    }
}
