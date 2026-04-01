package io.github.carlosyamanaka.cyphvv.application.ports.in;

import io.github.carlosyamanaka.cyphvv.application.core.domain.CardType;

public interface GetCardTypeByIdUseCase {
    CardType execute(Long worldId, Long cardTypeId);
}
