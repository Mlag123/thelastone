package org.mlagdevelopment.messenger.thelostone.client.service;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.jspecify.annotations.Nullable;
import org.mlagdevelopment.messenger.thelostone.client.ClientMain;
import org.mlagdevelopment.messenger.thelostone.server.dto.request.SendMessageRequest;
import org.mlagdevelopment.messenger.thelostone.server.dto.response.ChatMessageResponse;
import org.springframework.messaging.converter.JacksonJsonMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RoomControllerClient {



    public static String TEST_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI4IiwidXNlcm5hbWUiOiJURVNUTUxBRyIsImlhdCI6MTc4OTMxNTA1NiwiZXhwIjoxNzg5NDAxNDU2fQ.jnURq8o2hRjgnYb3RbjG9njK9wGdGCAs6r5SMP_if5A";

    private String url = "ws://localhost:8080/ws";

    public static String AUTHORIZATION = "Authorization";
    private final WebSocketStompClient client;
    private final JacksonJsonMessageConverter converter;
    private final JsonMapper mapper;

    private final WebSocketHttpHeaders handshakeHeaders;
    private final StompHeaders connectHeaders;

    public RoomControllerClient() {
        this.client = new WebSocketStompClient(new StandardWebSocketClient());
        client.setMessageConverter(new JacksonJsonMessageConverter());
        this.converter = new JacksonJsonMessageConverter();
        this.mapper = JsonMapper.builder().build();
        client.setMessageConverter(converter);
        this.handshakeHeaders = new WebSocketHttpHeaders();
        this.connectHeaders = new StompHeaders();
    }


    public void connectToWebSocketRoom(String token,Long room) {
        connectFromToken(token);

        try {
            StompSession session = client.connectAsync(url, handshakeHeaders, connectHeaders, new StompSessionHandlerAdapter() {
                @Override
                public void handleException(StompSession session, @Nullable StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                    ClientMain.log.error("Transport error: "+exception.getMessage());
                }

                @Override
                public void handleTransportError(StompSession session, Throwable exception) {
                    ClientMain.log.info("STOMP exception: "+exception.getMessage());
                }
            }).get(5, TimeUnit.SECONDS);

            ClientMain.log.info("Connected: "+session.isConnected()+"\n Session id: "+session.getSessionId());
            String roomDestination = "/topic/room."+room.toString();
            session.subscribe(roomDestination, new StompFrameHandler() {
                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return ChatMessageResponse.class;
                }

                @Override
                public void handleFrame(StompHeaders headers, @Nullable Object payload) {
                    ChatMessageResponse msg = (ChatMessageResponse) payload;
                    //DEBUG CODE!
                    ClientMain.log.info("<<[{}]: {}", msg.senderUiName(), msg.content());
                }
            });

            //DEBUG CODE!
            Scanner scanner = new Scanner(System.in);
            while (session.isConnected()){
                String word = scanner.nextLine();
                session.send("/app/chat.send",new SendMessageRequest(room,word));
            }



        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }


    }

    private void connectFromToken(String token){
        connectHeaders.add(AUTHORIZATION,"Bearer "+token);
    }


}
