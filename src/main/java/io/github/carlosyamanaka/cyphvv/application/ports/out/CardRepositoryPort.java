package io.github.carlosyamanaka.cyphvv.application.ports.out;

import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardSection;

import java.util.List;

public interface CardRepositoryPort {
    Card save(Card card);
    List<Card> findByWorldId(Long worldId);
    Card findById(Long worldId, Long cardId);
    Card findByIdWithSections(Long worldId, Long cardId, List<CardSection> sections);
}
