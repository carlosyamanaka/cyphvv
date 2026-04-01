package io.github.carlosyamanaka.cyphvv.application.ports.in;

import io.github.carlosyamanaka.cyphvv.application.core.domain.CardType;

import java.util.List;

public interface ListCardTypesByWorldUseCase {
    List<CardType> execute(Long worldId);
}
