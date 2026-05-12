package io.github.carlosyamanaka.cyphvv.adapters.out.repository;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.mapper.CardRepositoryMapper;
import io.github.carlosyamanaka.cyphvv.adapters.out.repository.mapper.CardSectionRepositoryMapper;
import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardSection;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardRelationship;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRepositoryPort;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRelationshipRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardRepositoryAdapter implements CardRepositoryPort {

    private final CardJpaRepository cardJpaRepository;
    private final CardSectionJpaRepository cardSectionJpaRepository;
    private final CardRepositoryMapper mapper;
    private final CardSectionRepositoryMapper sectionMapper;
    private final CardRelationshipRepositoryPort cardRelationshipRepositoryPort;

    public CardRepositoryAdapter(CardJpaRepository cardJpaRepository,
            CardSectionJpaRepository cardSectionJpaRepository,
            CardRepositoryMapper mapper,
            CardSectionRepositoryMapper sectionMapper,
            CardRelationshipRepositoryPort cardRelationshipRepositoryPort) {
        this.cardJpaRepository = cardJpaRepository;
        this.cardSectionJpaRepository = cardSectionJpaRepository;
        this.mapper = mapper;
        this.sectionMapper = sectionMapper;
        this.cardRelationshipRepositoryPort = cardRelationshipRepositoryPort;
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
                    List<CardRelationship> relationships = cardRelationshipRepositoryPort.findByCardId(entity.getId());
                    return mapper.toDomainWithAllComponents(entity, sections, relationships);
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
                    List<CardRelationship> relationships = cardRelationshipRepositoryPort.findByCardId(entity.getId());
                    return mapper.toDomainWithAllComponents(entity, sections, relationships);
                })
                .orElse(null);
    }

    @Override
    public Card findByIdWithSections(Long worldId, Long cardId, List<CardSection> sections) {
        return cardJpaRepository.findByWorldIdAndIdAndNotDeleted(worldId, cardId)
                .map(entity -> {
                    List<CardRelationship> relationships = cardRelationshipRepositoryPort.findByCardId(entity.getId());
                    return mapper.toDomainWithAllComponents(entity, sections, relationships);
                })
                .orElse(null);
    }
}
