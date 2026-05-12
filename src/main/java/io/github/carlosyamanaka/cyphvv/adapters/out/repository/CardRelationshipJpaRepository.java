package io.github.carlosyamanaka.cyphvv.adapters.out.repository;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardRelationshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRelationshipJpaRepository extends JpaRepository<CardRelationshipEntity, Long> {
    @Query("SELECT r FROM CardRelationshipEntity r WHERE r.originCardId = :originCardId AND r.deleted = false")
    List<CardRelationshipEntity> findByOriginCardIdAndNotDeleted(@Param("originCardId") Long originCardId);
}
