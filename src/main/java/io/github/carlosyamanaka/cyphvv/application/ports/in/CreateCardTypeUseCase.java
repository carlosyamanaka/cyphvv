package io.github.carlosyamanaka.cyphvv.application.ports.in;

import io.github.carlosyamanaka.cyphvv.application.core.domain.CardType;

public interface CreateCardTypeUseCase {
    CardType execute(Long worldId, String cardTypeName, String iconType);
}
