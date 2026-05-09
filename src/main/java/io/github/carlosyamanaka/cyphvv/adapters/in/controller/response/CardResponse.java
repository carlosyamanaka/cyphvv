package io.github.carlosyamanaka.cyphvv.adapters.in.controller.response;

import java.time.OffsetDateTime;
import java.util.List;

public record CardResponse(
                Long id,
                Long worldId,
                Long cardTypeId,
                String cardName,
                String imageUrl,
                List<String> aliases,
                List<CardSectionResponse> sections,
                OffsetDateTime createdAt) {
}
