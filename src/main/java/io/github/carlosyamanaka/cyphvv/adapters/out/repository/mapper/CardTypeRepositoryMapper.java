package io.github.carlosyamanaka.cyphvv.adapters.out.repository.mapper;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardTypeEntity;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardType;
import org.springframework.stereotype.Component;

@Component
public class CardTypeRepositoryMapper {

    public CardTypeEntity toEntity(CardType cardType) {
        CardTypeEntity entity = new CardTypeEntity();
        entity.setId(cardType.getId());
        entity.setWorldId(cardType.getWorldId());
        entity.setCardTypeName(cardType.getCardTypeName());
        entity.setIconType(cardType.getIconType());
        entity.setCreatedAt(cardType.getCreatedAt());
        entity.setDeleted(cardType.getDeleted());
        entity.setDeletedAt(cardType.getDeletedAt());
        return entity;
    }

    public CardType toDomain(CardTypeEntity entity) {
        return new CardType(
                entity.getId(),
                entity.getWorldId(),
                entity.getCardTypeName(),
                entity.getIconType(),
                entity.getCreatedAt(),
                entity.getDeleted(),
                entity.getDeletedAt());
    }
}
