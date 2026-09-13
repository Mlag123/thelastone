package org.mlagdevelopment.messenger.thelostone.server.service;

import org.mlagdevelopment.messenger.thelostone.server.domain.Message;
import org.mlagdevelopment.messenger.thelostone.server.domain.MessageType;
import org.mlagdevelopment.messenger.thelostone.server.domain.User;
import org.mlagdevelopment.messenger.thelostone.server.dto.ChatMessage;
import org.mlagdevelopment.messenger.thelostone.server.dto.SendMessageRequest;
import org.mlagdevelopment.messenger.thelostone.server.dto.UserResponse;
import org.mlagdevelopment.messenger.thelostone.server.repository.MessageRepository;
import org.mlagdevelopment.messenger.thelostone.server.repository.RoomMemberRepository;
import org.mlagdevelopment.messenger.thelostone.server.repository.UserRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageService(MessageRepository messageRepository, RoomMemberRepository roomMemberRepository, UserRepository userRepository, SimpMessagingTemplate messagingTemplate) {
        this.messageRepository = messageRepository;
        this.roomMemberRepository = roomMemberRepository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }


    @Transactional
    public void sendMessage(Long senderId, SendMessageRequest request){
        if(!roomMemberRepository.existsByRoomIdAndUserId(request.roomId(),senderId)){
            throw  new IllegalArgumentException("not a member of this room");
        }
        if(request.content() == null || request.content().isBlank()){
            throw  new IllegalArgumentException("Message content is empty");
        }
        Message message = new Message();
        message.setRoomId(request.roomId());
        message.setSenderId(senderId);
        message.setContent(request.content());
        message.setType(MessageType.TEXT);
        Message saved = messageRepository.save(message);

        User sender = userRepository.findById(senderId).orElseThrow();
        ChatMessage dto  =new ChatMessage(
                saved.getId(),
                saved.getRoomId(),
                saved.getSenderId(),
                sender.getUiName(),
                saved.getContent(),
                saved.getCreatedAt()
        );
        messagingTemplate.convertAndSend("/topic/room"+request.roomId(),dto);
    }
/*    public List<ChatMessage> getHistory(Long roomId, int limit) {
        return messageRepository.findTop50ByRoomIdOrderByCreatedAtDesc(roomId)
                .stream()
                .map(this::toDto)
                .toList();
    }*/
}
