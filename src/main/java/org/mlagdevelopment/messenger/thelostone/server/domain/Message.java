package org.mlagdevelopment.messenger.thelostone.server.domain;

import jakarta.persistence.*;
import org.mlagdevelopment.messenger.thelostone.server.domain.types.MessageType;

import java.time.Instant;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(nullable = false,length = 4000)
    private String content;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private MessageType type;

    @Column(nullable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist(){
        if (createdAt==null){
            createdAt = Instant.now();
        }
    }

}
