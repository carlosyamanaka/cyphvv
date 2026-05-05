package io.github.carlosyamanaka.cyphvv.application.ports.in;

import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;

public interface CreateCardUseCase {
    Card execute(Long worldId, Long cardTypeId, String cardName, String description, String imageUrl);

    default Card execute(Long worldId, Long cardTypeId, String imageUrl) {
        return execute(worldId, cardTypeId, null, null, imageUrl);
    }
}
