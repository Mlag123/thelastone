package org.mlagdevelopment.messenger.thelostone.server.config;

import org.jspecify.annotations.Nullable;
import org.mlagdevelopment.messenger.thelostone.server.service.JwtService;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
public class WebSocketAuthConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtService jwtService;


    public WebSocketAuthConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public @Nullable Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = StompHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (StompCommand.CONNECT.equals(accessor.getCommand())){
                    String authHeader = accessor.getFirstNativeHeader("Authorization");
                    if(authHeader == null|| !authHeader.startsWith("Bearer ")){
                        throw new IllegalArgumentException("Missing token");
                    }
                    String token = authHeader.substring(7);
                    if(!jwtService.isValid(token)){
                        throw new IllegalArgumentException("Invalid token");
                    }

                    Long userId = jwtService.extractUserId(token);
                    accessor.setUser(new UsernamePasswordAuthenticationToken(userId.toString(),null, List.of()));
                }
                return message;
            }

        });
    }
}
