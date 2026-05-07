package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.CardType;
import io.github.carlosyamanaka.cyphvv.application.ports.in.CreateCardTypeUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardTypeRepositoryPort;

import java.time.OffsetDateTime;

public class CreateCardTypeUseCaseImpl implements CreateCardTypeUseCase {

    private final CardTypeRepositoryPort cardTypeRepositoryPort;

    public CreateCardTypeUseCaseImpl(CardTypeRepositoryPort cardTypeRepositoryPort) {
        this.cardTypeRepositoryPort = cardTypeRepositoryPort;
    }

    @Override
    public CardType execute(Long worldId, String cardTypeName, String iconType) {
        if (worldId == null || worldId <= 0) {
            throw new IllegalArgumentException("World ID must be valid");
        }

        if (cardTypeName == null || cardTypeName.isBlank()) {
            throw new IllegalArgumentException("Card type name must be provided");
        }

        CardType newCardType = new CardType(
                null,
                worldId,
                cardTypeName.trim(),
                iconType,
                OffsetDateTime.now(),
                false,
                null);

        return cardTypeRepositoryPort.save(newCardType);
    }
}
