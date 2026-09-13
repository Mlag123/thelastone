package org.mlagdevelopment.messenger.thelostone.server.dto;

public record LoginResponse(
        String token,
        UserResponse user


) {
}
