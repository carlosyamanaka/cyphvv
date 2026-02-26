package io.github.carlosyamanaka.cyphvv.adapters.in.controller.response;

import java.time.OffsetDateTime;

public record WorldResponse(
        Long id,
        String userId,
        OffsetDateTime createdAt,
        String worldName
) {
}