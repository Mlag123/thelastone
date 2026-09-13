package org.mlagdevelopment.messenger.thelostone.server.controllers;

import org.mlagdevelopment.messenger.thelostone.server.dto.request.SendMessageRequest;
import org.mlagdevelopment.messenger.thelostone.server.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatController {

    private final MessageService messageService;

    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/chat.send")
    public void send(@Payload SendMessageRequest request, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        messageService.sendMessage(userId, request);
    }


}
