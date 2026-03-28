package io.github.carlosyamanaka.cyphvv.application.ports.out;

import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;

import java.util.List;

public interface CardRepositoryPort {
    List<Card> findByWorldId(Long worldId);
}
