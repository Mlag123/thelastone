package org.mlagdevelopment.messenger.thelostone.server.dto.response;

import java.time.Instant;

public record ChatMessageResponse(
        Long id,
        Long roomId,
        Long senderId,
        String senderUiName,
        String content,
        Instant createdAt
) {
}
