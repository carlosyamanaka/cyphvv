package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.ports.in.DeleteCardUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRepositoryPort;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardSectionRepositoryPort;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRelationshipRepositoryPort;

import java.util.Collections;

public class DeleteCardUseCaseImpl implements DeleteCardUseCase {

    private final CardRepositoryPort cardRepositoryPort;
    private final CardSectionRepositoryPort cardSectionRepositoryPort;
    private final CardRelationshipRepositoryPort cardRelationshipRepositoryPort;

    public DeleteCardUseCaseImpl(CardRepositoryPort cardRepositoryPort,
                                 CardSectionRepositoryPort cardSectionRepositoryPort,
                                 CardRelationshipRepositoryPort cardRelationshipRepositoryPort) {
        this.cardRepositoryPort = cardRepositoryPort;
        this.cardSectionRepositoryPort = cardSectionRepositoryPort;
        this.cardRelationshipRepositoryPort = cardRelationshipRepositoryPort;
    }

    @Override
    public void execute(Long worldId, Long cardId) {
        if (worldId == null || worldId <= 0) {
            throw new IllegalArgumentException("World ID must be valid");
        }

        if (cardId == null || cardId <= 0) {
            throw new IllegalArgumentException("Card ID must be valid");
        }

        Card card = cardRepositoryPort.findById(worldId, cardId);
        if (card == null) {
            throw new IllegalArgumentException("Card not found");
        }

        // Soft-delete the card (sets deleted flag and clears aliases list)
        card.delete();
        cardRepositoryPort.save(card);

        // Soft-delete related card sections
        cardSectionRepositoryPort.softDeleteByCardId(cardId);

        // Soft-delete relationships where the card is the origin card
        cardRelationshipRepositoryPort.saveAll(cardId, Collections.emptyList());
    }
}
