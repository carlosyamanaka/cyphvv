package io.github.carlosyamanaka.cyphvv.adapters.out.repository;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardRelationshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CardRelationshipJpaRepository extends JpaRepository<CardRelationshipEntity, Long> {

    @Query("SELECT r FROM CardRelationshipEntity r WHERE r.originCardId = :originCardId AND r.deleted = false")
    List<CardRelationshipEntity> findByOriginCardIdAndNotDeleted(@Param("originCardId") Long originCardId);

    /**
     * Busca apenas os IDs das relationships ativas de um card.
     * Usado para obter IDs antes de bulk-deletar targets, sem carregar entidades completas.
     */
    @Query("SELECT r.id FROM CardRelationshipEntity r WHERE r.originCardId = :originCardId AND r.deleted = false")
    List<Long> findIdsByOriginCardIdAndNotDeleted(@Param("originCardId") Long originCardId);

    /**
     * Soft-delete em massa de todas as relationships de um card.
     * Substitui o loop de saves individuais, reduzindo de N queries para 1 UPDATE.
     */
    @Modifying
    @Transactional
    @Query("UPDATE CardRelationshipEntity r SET r.deleted = true WHERE r.originCardId = :originCardId AND r.deleted = false")
    void softDeleteByOriginCardId(@Param("originCardId") Long originCardId);

    @Modifying
    @Transactional
    @Query("UPDATE CardRelationshipEntity r SET r.deleted = true WHERE r.originCardId IN (SELECT c.id FROM CardEntity c WHERE c.worldId = :worldId) AND r.deleted = false")
    void softDeleteByWorldId(@Param("worldId") Long worldId);
}
