package io.github.carlosyamanaka.cyphvv.adapters.out.repository;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardRelationshipEntity;
import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardRelationshipTargetEntity;
import io.github.carlosyamanaka.cyphvv.adapters.out.repository.mapper.CardRelationshipRepositoryMapper;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardRelationship;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardRelationshipTarget;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRelationshipRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CardRelationshipRepositoryAdapter implements CardRelationshipRepositoryPort {

    private final CardRelationshipJpaRepository relationshipJpaRepository;
    private final CardRelationshipTargetJpaRepository targetJpaRepository;
    private final CardRelationshipRepositoryMapper mapper;

    public CardRelationshipRepositoryAdapter(CardRelationshipJpaRepository relationshipJpaRepository,
                                           CardRelationshipTargetJpaRepository targetJpaRepository,
                                           CardRelationshipRepositoryMapper mapper) {
        this.relationshipJpaRepository = relationshipJpaRepository;
        this.targetJpaRepository = targetJpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void saveAll(Long cardId, List<CardRelationship> relationships) {
        // Soft delete existing relationships for this card
        List<CardRelationshipEntity> existing = relationshipJpaRepository.findByOriginCardIdAndNotDeleted(cardId);
        for (CardRelationshipEntity entity : existing) {
            entity.setDeleted(true);
            relationshipJpaRepository.save(entity);
            
            // Soft delete targets too
            List<CardRelationshipTargetEntity> targets = targetJpaRepository.findByRelationshipId(entity.getId());
            for (CardRelationshipTargetEntity target : targets) {
                target.setDeleted(true);
                targetJpaRepository.save(target);
            }
        }

        // Save new ones
        if (relationships != null) {
            for (CardRelationship relationship : relationships) {
                CardRelationshipEntity entity = mapper.toEntity(relationship);
                entity.setOriginCardId(cardId);
                entity.setDeleted(false);
                entity.setId(null); // Ensure it's a new entry
                
                CardRelationshipEntity savedEntity = relationshipJpaRepository.save(entity);
                
                if (relationship.getTargets() != null) {
                    for (CardRelationshipTarget target : relationship.getTargets()) {
                        CardRelationshipTargetEntity targetEntity = new CardRelationshipTargetEntity(savedEntity.getId(), target.getTargetCardId());
                        targetEntity.setDeleted(false);
                        targetJpaRepository.save(targetEntity);
                    }
                }
            }
        }
    }

    @Override
    public List<CardRelationship> findByCardId(Long cardId) {
        return relationshipJpaRepository.findByOriginCardIdAndNotDeleted(cardId)
                .stream()
                .map(entity -> {
                    List<CardRelationshipTargetEntity> targets = targetJpaRepository.findByRelationshipId(entity.getId());
                    entity.setTargets(targets);
                    return mapper.toDomain(entity);
                })
                .toList();
    }
}
