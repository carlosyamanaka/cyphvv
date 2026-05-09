package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardSection;
import io.github.carlosyamanaka.cyphvv.application.ports.in.SaveCardSectionsUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRepositoryPort;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardSectionRepositoryPort;

import java.time.OffsetDateTime;
import java.util.List;

public class SaveCardSectionsUseCaseImpl implements SaveCardSectionsUseCase {

    private final CardRepositoryPort cardRepositoryPort;
    private final CardSectionRepositoryPort cardSectionRepositoryPort;

    public SaveCardSectionsUseCaseImpl(CardRepositoryPort cardRepositoryPort,
            CardSectionRepositoryPort cardSectionRepositoryPort) {
        this.cardRepositoryPort = cardRepositoryPort;
        this.cardSectionRepositoryPort = cardSectionRepositoryPort;
    }

    @Override
    public Card execute(Long worldId, Long cardId, List<CardSection> sections) {
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

        // Soft-delete all existing sections, then save the new ones
        cardSectionRepositoryPort.softDeleteByCardId(cardId);

        if (sections != null) {
            for (CardSection section : sections) {
                CardSection newSection = new CardSection(
                        null,
                        cardId,
                        section.getType(),
                        section.getContent(),
                        OffsetDateTime.now(),
                        false);
                cardSectionRepositoryPort.save(newSection);
            }
        }

        // Return card with fresh sections
        List<CardSection> savedSections = cardSectionRepositoryPort.findByCardId(cardId);
        return cardRepositoryPort.findByIdWithSections(worldId, cardId, savedSections);
    }
}
