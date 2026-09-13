package org.mlagdevelopment.messenger.thelostone.server.domain;

import jakarta.persistence.*;
import org.mlagdevelopment.messenger.thelostone.server.domain.types.RoomType;

import java.time.Instant;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist(){
        if (createdAt ==null){
            createdAt = Instant.now();
        }
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }


}


