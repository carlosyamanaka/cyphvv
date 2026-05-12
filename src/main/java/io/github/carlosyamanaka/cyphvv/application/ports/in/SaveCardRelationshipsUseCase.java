package io.github.carlosyamanaka.cyphvv.application.ports.in;

import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardRelationship;
import java.util.List;

public interface SaveCardRelationshipsUseCase {
    Card execute(Long worldId, Long cardId, List<CardRelationship> relationships);
}
