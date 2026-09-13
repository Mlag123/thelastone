package org.mlagdevelopment.messenger.thelostone.server.dto;

import java.time.Instant;

public record ChatMessage(
        Long id,
        Long roomId,
        Long sender_id,
        String senderUiName,
        String content,
        Instant createdAt
) {
}
