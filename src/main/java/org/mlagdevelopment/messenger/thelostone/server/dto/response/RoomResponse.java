package org.mlagdevelopment.messenger.thelostone.server.dto.response;

import java.time.Instant;

public record RoomResponse (
        Long id,
        String name,
        String type,
        Long ownerId,
        Instant createdAt,
        long memberCount
){
}
