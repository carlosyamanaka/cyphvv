package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.CardType;
import io.github.carlosyamanaka.cyphvv.application.ports.in.UpdateCardTypeUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardTypeRepositoryPort;

public class UpdateCardTypeUseCaseImpl implements UpdateCardTypeUseCase {

    private final CardTypeRepositoryPort cardTypeRepositoryPort;

    public UpdateCardTypeUseCaseImpl(CardTypeRepositoryPort cardTypeRepositoryPort) {
        this.cardTypeRepositoryPort = cardTypeRepositoryPort;
    }

    @Override
    public CardType execute(Long worldId, Long cardTypeId, String cardTypeName) {
        if (worldId == null || worldId <= 0) {
            throw new IllegalArgumentException("World ID must be valid");
        }

        if (cardTypeId == null || cardTypeId <= 0) {
            throw new IllegalArgumentException("Card type ID must be valid");
        }

        if (cardTypeName == null || cardTypeName.isBlank()) {
            throw new IllegalArgumentException("Card type name must be provided");
        }

        CardType existingCardType = cardTypeRepositoryPort.findByIdAndWorldId(cardTypeId, worldId);
        if (existingCardType == null) {
            throw new IllegalArgumentException("Card type not found");
        }

        existingCardType.updateName(cardTypeName.trim());
        return cardTypeRepositoryPort.save(existingCardType);
    }
}
