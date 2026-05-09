package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.ports.in.AddCardAliasUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRepositoryPort;

public class AddCardAliasUseCaseImpl implements AddCardAliasUseCase {

    private final CardRepositoryPort cardRepositoryPort;

    public AddCardAliasUseCaseImpl(CardRepositoryPort cardRepositoryPort) {
        this.cardRepositoryPort = cardRepositoryPort;
    }

    @Override
    public Card execute(Long worldId, Long cardId, String alias) {
        Card card = cardRepositoryPort.findById(worldId, cardId);
        if (card == null) {
            throw new IllegalArgumentException("Card not found");
        }

        card.addAlias(alias);
        return cardRepositoryPort.save(card);
    }
}
