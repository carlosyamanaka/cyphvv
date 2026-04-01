package io.github.carlosyamanaka.cyphvv.application.ports.out;

import io.github.carlosyamanaka.cyphvv.application.core.domain.CardType;

import java.util.List;

public interface CardTypeRepositoryPort {
    CardType save(CardType cardType);

    List<CardType> findByWorldId(Long worldId);

    CardType findByIdAndWorldId(Long id, Long worldId);
}
