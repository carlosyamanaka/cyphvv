package io.github.carlosyamanaka.cyphvv.adapters.out.repository;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardRelationshipTargetEntity;
import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardRelationshipTargetId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRelationshipTargetJpaRepository extends JpaRepository<CardRelationshipTargetEntity, CardRelationshipTargetId> {

    List<CardRelationshipTargetEntity> findByRelationshipId(Long relationshipId);

    /**
     * Batch fetch: busca todos os targets de uma lista de relationship IDs de uma vez.
     * Elimina o N+1 que ocorria ao buscar targets individualmente por relationship.
     */
    @Query("SELECT t FROM CardRelationshipTargetEntity t WHERE t.relationshipId IN :relationshipIds AND t.deleted = false")
    List<CardRelationshipTargetEntity> findByRelationshipIdInAndNotDeleted(@Param("relationshipIds") List<Long> relationshipIds);

    /**
     * Soft-delete em massa para todos os targets de uma lista de relationships.
     * Substitui o loop individual de saves, reduzindo de N*M queries para 1 UPDATE.
     */
    @Modifying
    @Query("UPDATE CardRelationshipTargetEntity t SET t.deleted = true WHERE t.relationshipId IN :relationshipIds AND t.deleted = false")
    void softDeleteByRelationshipIds(@Param("relationshipIds") List<Long> relationshipIds);

    void deleteByRelationshipId(Long relationshipId);
}
