package org.mlagdevelopment.messenger.thelostone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jspecify.annotations.Nullable;
import org.mlagdevelopment.messenger.thelostone.server.dto.ChatMessage;
import org.mlagdevelopment.messenger.thelostone.server.dto.SendMessageRequest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class StompTest {
    public static void main(String[] args) throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI4IiwidXNlcm5hbWUiOiJURVNUTUxBRyIsImlhdCI6MTc4OTMxNTA1NiwiZXhwIjoxNzg5NDAxNDU2fQ.jnURq8o2hRjgnYb3RbjG9njK9wGdGCAs6r5SMP_if5A";

        WebSocketStompClient client = new WebSocketStompClient(new StandardWebSocketClient());
        client.setMessageConverter(new MappingJackson2MessageConverter());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(mapper);

        client.setMessageConverter(converter);
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

            session.subscribe("/topic/room.4", new StompFrameHandler() {

                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return ChatMessage.class;
                }

                @Override
                public void handleFrame(StompHeaders headers, @Nullable Object payload) {
                    ChatMessage msg = (ChatMessage) payload;
                    System.out.println("<< ["+msg.senderUiName()+"]: "+msg.content());
                }
            });


            while (session.isConnected()){
                Scanner scanner = new Scanner(System.in);
                String word = scanner.nextLine();
                session.send("/app/chat.send",new SendMessageRequest(4L,word));
            }
            //  session.disconnect();
            //System.out.println("Disconnected");

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
