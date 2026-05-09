package io.github.carlosyamanaka.cyphvv.application.ports.out;

import io.github.carlosyamanaka.cyphvv.application.core.domain.CardSection;

import java.util.List;

public interface CardSectionRepositoryPort {
    List<CardSection> findByCardId(Long cardId);
    CardSection save(CardSection section);
    void softDeleteByCardId(Long cardId);
}
