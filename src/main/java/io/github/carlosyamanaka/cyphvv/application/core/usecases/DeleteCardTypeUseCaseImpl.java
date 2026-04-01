package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.CardType;
import io.github.carlosyamanaka.cyphvv.application.ports.in.DeleteCardTypeUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardTypeRepositoryPort;

public class DeleteCardTypeUseCaseImpl implements DeleteCardTypeUseCase {

    private final CardTypeRepositoryPort cardTypeRepositoryPort;

    public DeleteCardTypeUseCaseImpl(CardTypeRepositoryPort cardTypeRepositoryPort) {
        this.cardTypeRepositoryPort = cardTypeRepositoryPort;
    }

    @Override
    public void execute(Long worldId, Long cardTypeId) {
        if (worldId == null || worldId <= 0) {
            throw new IllegalArgumentException("World ID must be valid");
        }

        if (cardTypeId == null || cardTypeId <= 0) {
            throw new IllegalArgumentException("Card type ID must be valid");
        }

        CardType existingCardType = cardTypeRepositoryPort.findByIdAndWorldId(cardTypeId, worldId);
        if (existingCardType == null) {
            throw new IllegalArgumentException("Card type not found");
        }

        existingCardType.delete();
        cardTypeRepositoryPort.save(existingCardType);
    }
}
