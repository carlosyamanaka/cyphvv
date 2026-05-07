package io.github.carlosyamanaka.cyphvv.adapters.in.controller.response;

import java.time.OffsetDateTime;

public record CardTypeResponse(
                Long id,
                Long worldId,
                String cardTypeName,
                String iconType,
                OffsetDateTime createdAt) {
}
