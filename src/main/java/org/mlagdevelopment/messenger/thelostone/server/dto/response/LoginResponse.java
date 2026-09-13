package org.mlagdevelopment.messenger.thelostone.server.dto.response;

public record LoginResponse(
        String token,
        UserResponse user


) {
}
