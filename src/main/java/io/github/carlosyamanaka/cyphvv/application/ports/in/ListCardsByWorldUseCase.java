package io.github.carlosyamanaka.cyphvv.application.ports.in;

import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;

import java.util.List;

public interface ListCardsByWorldUseCase {
    List<Card> execute(Long worldId);
}
