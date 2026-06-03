package io.github.carlosyamanaka.cyphvv.adapters.out.repository;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardSectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CardSectionJpaRepository extends JpaRepository<CardSectionEntity, Long> {

    @Query("SELECT s FROM CardSectionEntity s WHERE s.cardId = :cardId AND s.deleted = false ORDER BY s.createdAt ASC")
    List<CardSectionEntity> findByCardIdAndNotDeleted(@Param("cardId") Long cardId);

    @Modifying
    @Transactional
    @Query("UPDATE CardSectionEntity s SET s.deleted = true WHERE s.cardId = :cardId AND s.deleted = false")
    void softDeleteByCardId(@Param("cardId") Long cardId);

    @Modifying
    @Transactional
    @Query("UPDATE CardSectionEntity s SET s.deleted = true WHERE s.cardId IN (SELECT c.id FROM CardEntity c WHERE c.worldId = :worldId) AND s.deleted = false")
    void softDeleteByWorldId(@Param("worldId") Long worldId);
}
