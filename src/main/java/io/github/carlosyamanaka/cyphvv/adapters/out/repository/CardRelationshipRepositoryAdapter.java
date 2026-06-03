package io.github.carlosyamanaka.cyphvv.adapters.out.repository;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardRelationshipEntity;
import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardRelationshipTargetEntity;
import io.github.carlosyamanaka.cyphvv.adapters.out.repository.mapper.CardRelationshipRepositoryMapper;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardRelationship;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardRelationshipTarget;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRelationshipRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CardRelationshipRepositoryAdapter implements CardRelationshipRepositoryPort {

    private final CardRelationshipJpaRepository relationshipJpaRepository;
    private final CardRelationshipTargetJpaRepository targetJpaRepository;
    private final CardRelationshipRepositoryMapper mapper;

    public CardRelationshipRepositoryAdapter(CardRelationshipJpaRepository relationshipJpaRepository,
                                             CardRelationshipTargetJpaRepository targetJpaRepository,
                                             CardRelationshipRepositoryMapper mapper) {
        this.relationshipJpaRepository = relationshipJpaRepository;
        this.targetJpaRepository = targetJpaRepository;
        this.mapper = mapper;
    }

    /**
     * Salva (upsert) as relationships de um card.
     *
     * Estratégia de performance:
     * 1. Obtém os IDs das relationships existentes em 1 query (apenas IDs, sem carregar entidades).
     * 2. Se houver IDs, executa 2 UPDATEs em massa (targets e relationships) em vez de N+M saves individuais.
     * 3. Insere os novos registros normalmente.
     *
     * Queries emitidas: 1 (IDs) + [0 ou 2 UPDATEs] + N inserts relationships + N*M inserts targets
     * vs. antes: 1 fetch + N saves rels + N queries targets + N*M saves targets
     */
    @Override
    @Transactional
    public void saveAll(Long cardId, List<CardRelationship> relationships) {
        // Passo 1: busca apenas os IDs das relationships existentes — 1 query leve
        List<Long> existingIds = relationshipJpaRepository.findIdsByOriginCardIdAndNotDeleted(cardId);

        if (!existingIds.isEmpty()) {
            // Passo 2a: soft-delete em massa dos targets — 1 UPDATE (era N queries + N*M saves)
            targetJpaRepository.softDeleteByRelationshipIds(existingIds);

            // Passo 2b: soft-delete em massa das relationships — 1 UPDATE (era N saves)
            relationshipJpaRepository.softDeleteByOriginCardId(cardId);
        }

        // Passo 3: insere os novos registros
        if (relationships != null) {
            for (CardRelationship relationship : relationships) {
                CardRelationshipEntity entity = mapper.toEntity(relationship);
                entity.setOriginCardId(cardId);
                entity.setDeleted(false);
                entity.setId(null); // garante que é um novo registro

                CardRelationshipEntity savedEntity = relationshipJpaRepository.save(entity);

                if (relationship.getTargets() != null) {
                    for (CardRelationshipTarget target : relationship.getTargets()) {
                        CardRelationshipTargetEntity targetEntity =
                                new CardRelationshipTargetEntity(savedEntity.getId(), target.getTargetCardId());
                        targetEntity.setDeleted(false);
                        targetJpaRepository.save(targetEntity);
                    }
                }
            }
        }
    }

    /**
     * Busca todas as relationships de um card com seus targets.
     *
     * Estratégia de performance (batch fetch — elimina N+1):
     * 1. Busca todas as relationships do card — 1 query.
     * 2. Coleta todos os IDs de relationships em memória.
     * 3. Busca TODOS os targets de uma vez com IN clause — 1 query.
     * 4. Agrupa targets por relationshipId em memória e monta o resultado.
     *
     * Total: 2 queries fixas (independente de quantas relationships existam).
     * Antes: 1 + N queries (N = número de relationships).
     */
    @Override
    @Transactional(readOnly = true)
    public List<CardRelationship> findByCardId(Long cardId) {
        List<CardRelationshipEntity> entities = relationshipJpaRepository.findByOriginCardIdAndNotDeleted(cardId);

        if (entities.isEmpty()) {
            return Collections.emptyList();
        }

        // Batch: coleta todos os IDs e busca os targets em uma única query
        List<Long> relationshipIds = entities.stream()
                .map(CardRelationshipEntity::getId)
                .toList();

        Map<Long, List<CardRelationshipTargetEntity>> targetsByRelId =
                targetJpaRepository.findByRelationshipIdInAndNotDeleted(relationshipIds)
                        .stream()
                        .collect(Collectors.groupingBy(CardRelationshipTargetEntity::getRelationshipId));

        return entities.stream()
                .map(entity -> {
                    entity.setTargets(targetsByRelId.getOrDefault(entity.getId(), Collections.emptyList()));
                    return mapper.toDomain(entity);
                })
                .toList();
    }

    @Override
    @Transactional
    public void softDeleteByWorldId(Long worldId) {
        targetJpaRepository.softDeleteTargetsByWorldId(worldId);
        targetJpaRepository.softDeleteExternalTargetsByWorldId(worldId);
        relationshipJpaRepository.softDeleteByWorldId(worldId);
    }
}
