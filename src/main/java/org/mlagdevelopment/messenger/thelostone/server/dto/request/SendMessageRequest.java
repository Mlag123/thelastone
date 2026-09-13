package org.mlagdevelopment.messenger.thelostone.server.dto.request;

public record SendMessageRequest(
        Long roomId,
        String content
) {
}
