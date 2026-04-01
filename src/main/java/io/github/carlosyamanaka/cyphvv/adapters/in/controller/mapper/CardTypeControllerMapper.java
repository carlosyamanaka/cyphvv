package io.github.carlosyamanaka.cyphvv.adapters.in.controller.mapper;

import io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardTypeResponse;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardType;
import org.springframework.stereotype.Component;

@Component
public class CardTypeControllerMapper {

    public CardTypeResponse toResponse(CardType cardType) {
        return new CardTypeResponse(
                cardType.getId(),
                cardType.getWorldId(),
                cardType.getCardTypeName(),
                cardType.getCreatedAt());
    }
}
