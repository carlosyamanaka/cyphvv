package io.github.carlosyamanaka.cyphvv.adapters.in.controller.mapper;

import io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardResponse;
import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import org.springframework.stereotype.Component;

@Component
public class CardControllerMapper {

    public CardResponse toResponse(Card card) {
        return new CardResponse(
                card.getId(),
                card.getWorldId(),
                card.getCardTypeId(),
                card.getImageUrl(),
                card.getAliases(),
                card.getCreatedAt()
        );
    }
}
