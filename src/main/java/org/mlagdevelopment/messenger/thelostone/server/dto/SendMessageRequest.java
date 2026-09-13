package org.mlagdevelopment.messenger.thelostone.server.dto;

public record SendMessageRequest(
        Long roomId,
        String content
) {
}
