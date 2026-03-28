package io.github.carlosyamanaka.cyphvv.adapters.out.repository.mapper;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardEntity;
import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import org.springframework.stereotype.Component;

@Component
public class CardRepositoryMapper {

    public CardEntity toEntity(Card card) {
        CardEntity entity = new CardEntity();
        entity.setId(card.getId());
        entity.setWorldId(card.getWorldId());
        entity.setCardTypeId(card.getCardTypeId());
        entity.setImageUrl(card.getImageUrl());
        entity.setAliases(card.getAliases());
        entity.setCreatedAt(card.getCreatedAt());
        entity.setDeleted(card.getDeleted());
        entity.setDeletedAt(card.getDeletedAt());
        return entity;
    }

    public Card toDomain(CardEntity entity) {
        return new Card(
                entity.getId(),
                entity.getWorldId(),
                entity.getCardTypeId(),
                entity.getImageUrl(),
                entity.getAliases(),
                entity.getCreatedAt(),
                entity.getDeleted(),
                entity.getDeletedAt());
    }
}
