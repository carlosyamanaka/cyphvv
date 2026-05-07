package io.github.carlosyamanaka.cyphvv.application.ports.in;

import io.github.carlosyamanaka.cyphvv.application.core.domain.CardType;

public interface UpdateCardTypeUseCase {
    CardType execute(Long worldId, Long cardTypeId, String cardTypeName, String iconType);
}
