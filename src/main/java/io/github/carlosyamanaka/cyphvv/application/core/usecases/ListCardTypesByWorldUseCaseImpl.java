package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.CardType;
import io.github.carlosyamanaka.cyphvv.application.ports.in.ListCardTypesByWorldUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardTypeRepositoryPort;

import java.util.List;

public class ListCardTypesByWorldUseCaseImpl implements ListCardTypesByWorldUseCase {

    private final CardTypeRepositoryPort cardTypeRepositoryPort;

    public ListCardTypesByWorldUseCaseImpl(CardTypeRepositoryPort cardTypeRepositoryPort) {
        this.cardTypeRepositoryPort = cardTypeRepositoryPort;
    }

    @Override
    public List<CardType> execute(Long worldId) {
        if (worldId == null || worldId <= 0) {
            throw new IllegalArgumentException("World ID must be valid");
        }

        return cardTypeRepositoryPort.findByWorldId(worldId);
    }
}
