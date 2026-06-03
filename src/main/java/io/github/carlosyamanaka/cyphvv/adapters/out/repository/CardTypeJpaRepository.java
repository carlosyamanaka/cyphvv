package io.github.carlosyamanaka.cyphvv.adapters.out.repository;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CardTypeJpaRepository extends JpaRepository<CardTypeEntity, Long> {

    @Query("SELECT ct FROM CardTypeEntity ct WHERE ct.worldId = :worldId AND ct.deleted = false ORDER BY ct.createdAt DESC")
    List<CardTypeEntity> findByWorldIdAndNotDeleted(@Param("worldId") Long worldId);

    @Query("SELECT ct FROM CardTypeEntity ct WHERE ct.id = :id AND ct.worldId = :worldId AND ct.deleted = false")
    CardTypeEntity findByIdAndWorldIdAndNotDeleted(@Param("id") Long id, @Param("worldId") Long worldId);

    @Modifying
    @Transactional
    @Query("UPDATE CardTypeEntity ct SET ct.deleted = true, ct.deletedAt = :deletedAt WHERE ct.worldId = :worldId AND ct.deleted = false")
    void softDeleteByWorldId(@Param("worldId") Long worldId, @Param("deletedAt") java.time.OffsetDateTime deletedAt);
}
