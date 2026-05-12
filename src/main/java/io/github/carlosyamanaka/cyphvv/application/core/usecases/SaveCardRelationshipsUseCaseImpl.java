package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardRelationship;
import io.github.carlosyamanaka.cyphvv.application.ports.in.SaveCardRelationshipsUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRepositoryPort;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRelationshipRepositoryPort;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardSectionRepositoryPort;

import java.util.List;

public class SaveCardRelationshipsUseCaseImpl implements SaveCardRelationshipsUseCase {

    private final CardRepositoryPort cardRepositoryPort;
    private final CardRelationshipRepositoryPort cardRelationshipRepositoryPort;
    private final CardSectionRepositoryPort cardSectionRepositoryPort;

    public SaveCardRelationshipsUseCaseImpl(CardRepositoryPort cardRepositoryPort,
                                          CardRelationshipRepositoryPort cardRelationshipRepositoryPort,
                                          CardSectionRepositoryPort cardSectionRepositoryPort) {
        this.cardRepositoryPort = cardRepositoryPort;
        this.cardRelationshipRepositoryPort = cardRelationshipRepositoryPort;
        this.cardSectionRepositoryPort = cardSectionRepositoryPort;
    }

    @Override
    public Card execute(Long worldId, Long cardId, List<CardRelationship> relationships) {
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

        cardRelationshipRepositoryPort.saveAll(cardId, relationships);

        // Reload card with all components
        List<CardRelationship> savedRelationships = cardRelationshipRepositoryPort.findByCardId(cardId);
        // Note: we also need to load sections as the return type is Card and it usually includes everything
        var sections = cardSectionRepositoryPort.findByCardId(cardId);
        
        return new Card(
                card.getId(),
                card.getWorldId(),
                card.getCardTypeId(),
                card.getCardName(),
                card.getImageUrl(),
                card.getAliases(),
                sections,
                savedRelationships,
                card.getCreatedAt(),
                card.getDeleted(),
                card.getDeletedAt()
        );
    }
}
