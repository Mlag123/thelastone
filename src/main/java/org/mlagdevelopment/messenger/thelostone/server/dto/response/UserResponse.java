package org.mlagdevelopment.messenger.thelostone.server.dto.response;

import java.time.Instant;

public record UserResponse(
        Long id,
        String username,
        String uiName,
        Instant createdAt


) {
}
