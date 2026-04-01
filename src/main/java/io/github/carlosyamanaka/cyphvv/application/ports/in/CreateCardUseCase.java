package io.github.carlosyamanaka.cyphvv.application.ports.in;

import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;

public interface CreateCardUseCase {
    Card execute(Long worldId, Long cardTypeId, String imageUrl);
}
