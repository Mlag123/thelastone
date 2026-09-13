package org.mlagdevelopment.messenger.thelostone.server.dto;

import java.time.Instant;

public record ChatMessage(
        Long id,
        Long roomId,
        Long senderId,
        String senderUiName,
        String content,
        Instant createdAt
) {
}
