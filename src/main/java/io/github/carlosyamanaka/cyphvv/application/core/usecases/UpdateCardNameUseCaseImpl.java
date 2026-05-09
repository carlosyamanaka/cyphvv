package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.ports.in.UpdateCardNameUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRepositoryPort;

public class UpdateCardNameUseCaseImpl implements UpdateCardNameUseCase {

    private final CardRepositoryPort cardRepositoryPort;

    public UpdateCardNameUseCaseImpl(CardRepositoryPort cardRepositoryPort) {
        this.cardRepositoryPort = cardRepositoryPort;
    }

    @Override
    public Card execute(Long worldId, Long cardId, String cardName) {
        if (worldId == null || worldId <= 0) {
            throw new IllegalArgumentException("World ID must be valid");
        }
        if (cardId == null || cardId <= 0) {
            throw new IllegalArgumentException("Card ID must be valid");
        }
        if (cardName == null || cardName.isBlank()) {
            throw new IllegalArgumentException("Card name must be provided");
        }

        Card card = cardRepositoryPort.findById(worldId, cardId);
        if (card == null) {
            throw new IllegalArgumentException("Card not found");
        }

        card.updateName(cardName.trim());
        return cardRepositoryPort.save(card);
    }
}
