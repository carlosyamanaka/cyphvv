package io.github.carlosyamanaka.cyphvv.adapters.out.repository.mapper;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardEntity;
import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component
public class CardRepositoryMapper {

    public CardEntity toEntity(Card card) {
        CardEntity entity = new CardEntity();
        entity.setId(card.getId());
        entity.setWorldId(card.getWorldId());
        entity.setCardTypeId(card.getCardTypeId());
        entity.setCardName(card.getCardName());
        entity.setDescription(card.getDescription());
        entity.setImageUrl(card.getImageUrl());
        entity.setAliases(card.getAliases() == null ? null : card.getAliases().toArray(String[]::new));
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
                entity.getCardName(),
                entity.getDescription(),
                entity.getImageUrl(),
                entity.getAliases() == null ? Collections.emptyList() : Arrays.asList(entity.getAliases()),
                entity.getCreatedAt(),
                entity.getDeleted(),
                entity.getDeletedAt());
    }
}
