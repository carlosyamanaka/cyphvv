package io.github.carlosyamanaka.cyphvv.adapters.out.repository;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.mapper.CardSectionRepositoryMapper;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardSection;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardSectionRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardSectionRepositoryAdapter implements CardSectionRepositoryPort {

    private final CardSectionJpaRepository cardSectionJpaRepository;
    private final CardSectionRepositoryMapper mapper;

    public CardSectionRepositoryAdapter(CardSectionJpaRepository cardSectionJpaRepository,
            CardSectionRepositoryMapper mapper) {
        this.cardSectionJpaRepository = cardSectionJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CardSection> findByCardId(Long cardId) {
        return cardSectionJpaRepository.findByCardIdAndNotDeleted(cardId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public CardSection save(CardSection section) {
        return mapper.toDomain(cardSectionJpaRepository.save(mapper.toEntity(section)));
    }

    @Override
    public void softDeleteByCardId(Long cardId) {
        cardSectionJpaRepository.softDeleteByCardId(cardId);
    }
}
