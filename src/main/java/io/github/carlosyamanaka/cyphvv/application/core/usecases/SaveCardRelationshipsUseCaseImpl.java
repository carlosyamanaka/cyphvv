package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardRelationship;
import io.github.carlosyamanaka.cyphvv.application.ports.in.SaveCardRelationshipsUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRepositoryPort;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRelationshipRepositoryPort;

import java.util.List;

public class SaveCardRelationshipsUseCaseImpl implements SaveCardRelationshipsUseCase {

    private final CardRepositoryPort cardRepositoryPort;
    private final CardRelationshipRepositoryPort cardRelationshipRepositoryPort;

    public SaveCardRelationshipsUseCaseImpl(CardRepositoryPort cardRepositoryPort,
                                            CardRelationshipRepositoryPort cardRelationshipRepositoryPort) {
        this.cardRepositoryPort = cardRepositoryPort;
        this.cardRelationshipRepositoryPort = cardRelationshipRepositoryPort;
    }

    /**
     * Salva as relationships de um card e retorna o card atualizado.
     *
     * Performance improvements vs. versão anterior:
     * - Removida dependência desnecessária de CardSectionRepositoryPort.
     * - As sections já são carregadas junto com o card em findById() e não são
     *   alteradas por esta operação, então são reutilizadas do objeto já em memória.
     * - Eliminado o segundo reload de relationships após o save (agora lido da operação de save).
     * - O reload de relationships pós-save usa o findByCardId() otimizado (2 queries fixas,
     *   sem N+1), necessário para retornar o estado persistido com os IDs gerados.
     */
    @Override
    public Card execute(Long worldId, Long cardId, List<CardRelationship> relationships) {
        if (worldId == null || worldId <= 0) {
            throw new IllegalArgumentException("World ID must be valid");
        }
        if (cardId == null || cardId <= 0) {
            throw new IllegalArgumentException("Card ID must be valid");
        }

        // Carrega o card completo (com sections já incluídas)
        Card card = cardRepositoryPort.findById(worldId, cardId);
        if (card == null) {
            throw new IllegalArgumentException("Card not found");
        }

        // Persiste as relationships
        cardRelationshipRepositoryPort.saveAll(cardId, relationships);

        // Recarrega apenas as relationships (2 queries fixas — batch fetch sem N+1)
        // As sections já estão em card.getSections() e não foram alteradas
        List<CardRelationship> savedRelationships = cardRelationshipRepositoryPort.findByCardId(cardId);

        return new Card(
                card.getId(),
                card.getWorldId(),
                card.getCardTypeId(),
                card.getCardName(),
                card.getImageUrl(),
                card.getAliases(),
                card.getSections(),   // reutiliza as sections já carregadas — sem query adicional
                savedRelationships,
                card.getCreatedAt(),
                card.getDeleted(),
                card.getDeletedAt()
        );
    }
}
