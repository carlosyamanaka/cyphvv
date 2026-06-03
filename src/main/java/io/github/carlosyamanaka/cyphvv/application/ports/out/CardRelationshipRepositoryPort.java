package io.github.carlosyamanaka.cyphvv.application.ports.out;

import io.github.carlosyamanaka.cyphvv.application.core.domain.CardRelationship;
import java.util.List;

public interface CardRelationshipRepositoryPort {
    void saveAll(Long cardId, List<CardRelationship> relationships);
    List<CardRelationship> findByCardId(Long cardId);
    void softDeleteByWorldId(Long worldId);
}
