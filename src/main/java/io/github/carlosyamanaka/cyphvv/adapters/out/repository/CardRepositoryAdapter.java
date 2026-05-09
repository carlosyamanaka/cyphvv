package io.github.carlosyamanaka.cyphvv.adapters.out.repository;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.mapper.CardRepositoryMapper;
import io.github.carlosyamanaka.cyphvv.adapters.out.repository.mapper.CardSectionRepositoryMapper;
import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardSection;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardRepositoryAdapter implements CardRepositoryPort {

    private final CardJpaRepository cardJpaRepository;
    private final CardSectionJpaRepository cardSectionJpaRepository;
    private final CardRepositoryMapper mapper;
    private final CardSectionRepositoryMapper sectionMapper;

    public CardRepositoryAdapter(CardJpaRepository cardJpaRepository,
            CardSectionJpaRepository cardSectionJpaRepository,
            CardRepositoryMapper mapper,
            CardSectionRepositoryMapper sectionMapper) {
        this.cardJpaRepository = cardJpaRepository;
        this.cardSectionJpaRepository = cardSectionJpaRepository;
        this.mapper = mapper;
        this.sectionMapper = sectionMapper;
    }

    @Override
    public Card save(Card card) {
        return mapper.toDomain(cardJpaRepository.save(mapper.toEntity(card)));
    }

    @Override
    public List<Card> findByWorldId(Long worldId) {
        return cardJpaRepository.findByWorldIdAndNotDeleted(worldId)
                .stream()
                .map(entity -> {
                    List<CardSection> sections = cardSectionJpaRepository
                            .findByCardIdAndNotDeleted(entity.getId())
                            .stream()
                            .map(sectionMapper::toDomain)
                            .toList();
                    return mapper.toDomainWithSections(entity, sections);
                })
                .toList();
    }

    @Override
    public Card findById(Long worldId, Long cardId) {
        return cardJpaRepository.findByWorldIdAndIdAndNotDeleted(worldId, cardId)
                .map(entity -> {
                    List<CardSection> sections = cardSectionJpaRepository
                            .findByCardIdAndNotDeleted(entity.getId())
                            .stream()
                            .map(sectionMapper::toDomain)
                            .toList();
                    return mapper.toDomainWithSections(entity, sections);
                })
                .orElse(null);
    }

    @Override
    public Card findByIdWithSections(Long worldId, Long cardId, List<CardSection> sections) {
        return cardJpaRepository.findByWorldIdAndIdAndNotDeleted(worldId, cardId)
                .map(entity -> mapper.toDomainWithSections(entity, sections))
                .orElse(null);
    }
}
