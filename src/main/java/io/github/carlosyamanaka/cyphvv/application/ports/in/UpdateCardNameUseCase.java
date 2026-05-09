package io.github.carlosyamanaka.cyphvv.application.ports.in;

import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;

public interface UpdateCardNameUseCase {
    Card execute(Long worldId, Long cardId, String cardName);
}
