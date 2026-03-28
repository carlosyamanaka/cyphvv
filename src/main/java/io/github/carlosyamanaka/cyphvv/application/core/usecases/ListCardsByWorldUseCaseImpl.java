package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.ports.in.ListCardsByWorldUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRepositoryPort;

import java.util.List;

public class ListCardsByWorldUseCaseImpl implements ListCardsByWorldUseCase {

    private final CardRepositoryPort cardRepositoryPort;

    public ListCardsByWorldUseCaseImpl(CardRepositoryPort cardRepositoryPort) {
        this.cardRepositoryPort = cardRepositoryPort;
    }

    @Override
    public List<Card> execute(Long worldId) {
        if (worldId == null || worldId <= 0) {
            throw new IllegalArgumentException("World ID must be valid");
        }
        return cardRepositoryPort.findByWorldId(worldId);
    }
}
