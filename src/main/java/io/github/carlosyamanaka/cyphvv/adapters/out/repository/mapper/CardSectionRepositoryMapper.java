package io.github.carlosyamanaka.cyphvv.adapters.out.repository.mapper;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardSectionEntity;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardSection;
import org.springframework.stereotype.Component;

@Component
public class CardSectionRepositoryMapper {

    public CardSectionEntity toEntity(CardSection section) {
        CardSectionEntity entity = new CardSectionEntity();
        entity.setId(section.getId());
        entity.setCardId(section.getCardId());
        entity.setType(section.getType());
        entity.setContent(section.getContent());
        entity.setCreatedAt(section.getCreatedAt());
        entity.setDeleted(section.getDeleted());
        return entity;
    }

    public CardSection toDomain(CardSectionEntity entity) {
        return new CardSection(
                entity.getId(),
                entity.getCardId(),
                entity.getType(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getDeleted());
    }
}
