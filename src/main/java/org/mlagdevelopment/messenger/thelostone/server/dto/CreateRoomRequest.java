package org.mlagdevelopment.messenger.thelostone.server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateRoomRequest(
        @NotBlank
        @Size(min = 1, max = 100)
        String name

) {
}
