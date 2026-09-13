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
import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class RoomClampService {

    public static String TEST_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI4IiwidXNlcm5hbWUiOiJURVNUTUxBRyIsImlhdCI6MTc4OTMxNTA1NiwiZXhwIjoxNzg5NDAxNDU2fQ.jnURq8o2hRjgnYb3RbjG9njK9wGdGCAs6r5SMP_if5A";
    private String url = "ws://localhost:8080/ws"; //HARDCODE!
    private final WebSocketStompClient client;
    private final JacksonJsonMessageConverter converter;
    private final JsonMapper mapper;

    private final WebSocketHttpHeaders handshakeHeaders;
    private final StompHeaders connectHeaders;
    private StompSession session;
    private Long currentRoomId;

    public RoomClampService() {
        this.client = new WebSocketStompClient(new StandardWebSocketClient());
        client.setMessageConverter(new JacksonJsonMessageConverter());
        this.converter = new JacksonJsonMessageConverter();
        this.mapper = JsonMapper.builder().build();
        client.setMessageConverter(converter);
        this.handshakeHeaders = new WebSocketHttpHeaders();
        this.connectHeaders = new StompHeaders();
    }


    public void connect(String token) {
        connectHeaders.add(AUTHORIZATION, "Bearer " + token);
        try {
            this.session = client.connectAsync(url, handshakeHeaders, connectHeaders, new StompSessionHandlerAdapter() {
            }).get(5, TimeUnit.SECONDS);
            ClientMain.log.info("Connected: {}", session.getSessionId());
        } catch (Exception ex) {
            ClientMain.log.error(ex.getMessage());
        }
    }

    public void subscribeToRoom(Long roomId) {
        this.currentRoomId = roomId;
        String roomDestination = "/topic/room." + roomId;
        session.subscribe(roomDestination, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatMessageResponse.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, @Nullable Object payload) {
                ChatMessageResponse msg = (ChatMessageResponse) payload;

                ///debug code. Суть идеи такая, что при прихода сообщения, будет добавлятся в массив сообщений. Реализация будет на самом устройстве. Или тут. Подумаю.
                ClientMain.log.info("<<[{}]: {}", msg.senderUiName(), msg.content());

            }
        });
        ClientMain.log.info("Subcribe to {}", roomDestination);
    }

    public void sendMessage(String content) {
        if (session == null || !session.isConnected()) {
            throw new IllegalStateException("Not a connect");
        }
        if (currentRoomId == null) {
            throw new IllegalStateException("Not in a room");
        }
        session.send("/app/chat.send", new SendMessageRequest(currentRoomId, content));

    }

    public void disconnect() {
        if (session != null && session.isConnected()) {
            session.disconnect();
            ClientMain.log.info("Disconnected");
        }
    }

}
