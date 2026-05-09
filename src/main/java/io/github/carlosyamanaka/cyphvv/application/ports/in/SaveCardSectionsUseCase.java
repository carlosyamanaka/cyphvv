package io.github.carlosyamanaka.cyphvv.application.ports.in;

import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardSection;

import java.util.List;

public interface SaveCardSectionsUseCase {
    Card execute(Long worldId, Long cardId, List<CardSection> sections);
}
