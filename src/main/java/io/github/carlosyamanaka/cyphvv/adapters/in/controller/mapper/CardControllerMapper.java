package io.github.carlosyamanaka.cyphvv.adapters.in.controller.mapper;

import io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardResponse;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardRelationshipResponse;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardSectionResponse;
import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CardControllerMapper {

    public CardResponse toResponse(Card card) {
        List<CardSectionResponse> sectionResponses = card.getSections() == null
                ? Collections.emptyList()
                : card.getSections().stream()
                        .map(s -> new CardSectionResponse(s.getId(), s.getType(), s.getContent()))
                        .toList();

        List<CardRelationshipResponse> relationshipResponses = card.getRelationships() == null
                ? Collections.emptyList()
                : card.getRelationships().stream()
                        .map(r -> {
                            List<CardRelationshipResponse.CardRelationshipTargetResponse> targets = r.getTargets() == null
                                    ? Collections.emptyList()
                                    : r.getTargets().stream()
                                            .map(t -> new CardRelationshipResponse.CardRelationshipTargetResponse(t.getTargetCardId()))
                                            .toList();
                            return new CardRelationshipResponse(r.getId(), r.getName(), targets);
                        })
                        .toList();

        return new CardResponse(
                card.getId(),
                card.getWorldId(),
                card.getCardTypeId(),
                card.getCardName(),
                card.getImageUrl(),
                card.getAliases(),
                sectionResponses,
                relationshipResponses,
                card.getCreatedAt());
    }
}
