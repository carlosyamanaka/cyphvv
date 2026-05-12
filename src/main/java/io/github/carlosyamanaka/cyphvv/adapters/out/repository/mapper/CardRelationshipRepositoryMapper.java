package io.github.carlosyamanaka.cyphvv.adapters.out.repository.mapper;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardRelationshipEntity;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardRelationship;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardRelationshipTarget;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CardRelationshipRepositoryMapper {

    public CardRelationshipEntity toEntity(CardRelationship relationship) {
        CardRelationshipEntity entity = new CardRelationshipEntity();
        entity.setId(relationship.getId());
        entity.setName(relationship.getName());
        entity.setOriginCardId(relationship.getOriginCardId());
        entity.setDeleted(relationship.getDeleted());
        
        // Targets are handled manually by the Repository Adapter to avoid JPA cascading issues with null IDs
        return entity;
    }

    public CardRelationship toDomain(CardRelationshipEntity entity) {
        List<CardRelationshipTarget> targets = entity.getTargets() == null
                ? Collections.emptyList()
                : entity.getTargets().stream()
                        .filter(t -> t.getDeleted() != null && !t.getDeleted())
                        .map(t -> new CardRelationshipTarget(t.getTargetCardId()))
                        .toList();

        return new CardRelationship(
                entity.getId(),
                entity.getName(),
                entity.getOriginCardId(),
                targets,
                entity.getDeleted()
        );
    }
}
