package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.ports.in.CreateCardUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRepositoryPort;

import java.time.OffsetDateTime;
import java.util.List;

public class CreateCardUseCaseImpl implements CreateCardUseCase {

    private final CardRepositoryPort cardRepositoryPort;

    public CreateCardUseCaseImpl(CardRepositoryPort cardRepositoryPort) {
        this.cardRepositoryPort = cardRepositoryPort;
    }

    @Override
    public Card execute(Long worldId, Long cardTypeId, String imageUrl) {
        if (worldId == null || worldId <= 0) {
            throw new IllegalArgumentException("World ID must be valid");
        }

        if (cardTypeId == null || cardTypeId <= 0) {
            throw new IllegalArgumentException("Card type ID must be valid");
        }

        Card newCard = new Card(
                null,
                worldId,
                cardTypeId,
                imageUrl,
                List.of(),
                OffsetDateTime.now(),
                false,
                null);

        return cardRepositoryPort.save(newCard);
    }
}
