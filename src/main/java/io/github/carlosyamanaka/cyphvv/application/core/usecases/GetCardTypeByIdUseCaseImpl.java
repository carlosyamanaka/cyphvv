package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.CardType;
import io.github.carlosyamanaka.cyphvv.application.ports.in.GetCardTypeByIdUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardTypeRepositoryPort;

public class GetCardTypeByIdUseCaseImpl implements GetCardTypeByIdUseCase {

    private final CardTypeRepositoryPort cardTypeRepositoryPort;

    public GetCardTypeByIdUseCaseImpl(CardTypeRepositoryPort cardTypeRepositoryPort) {
        this.cardTypeRepositoryPort = cardTypeRepositoryPort;
    }

    @Override
    public CardType execute(Long worldId, Long cardTypeId) {
        if (worldId == null || worldId <= 0) {
            throw new IllegalArgumentException("World ID must be valid");
        }

        if (cardTypeId == null || cardTypeId <= 0) {
            throw new IllegalArgumentException("Card type ID must be valid");
        }

        CardType cardType = cardTypeRepositoryPort.findByIdAndWorldId(cardTypeId, worldId);
        if (cardType == null) {
            throw new IllegalArgumentException("Card type not found");
        }

        return cardType;
    }
}
