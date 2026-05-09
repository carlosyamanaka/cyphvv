package io.github.carlosyamanaka.cyphvv.adapters.out.repository;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardJpaRepository extends JpaRepository<CardEntity, Long> {
    @Query("SELECT c FROM CardEntity c WHERE c.worldId = :worldId AND c.deleted = false ORDER BY c.createdAt DESC")
    List<CardEntity> findByWorldIdAndNotDeleted(@Param("worldId") Long worldId);

    @Query("SELECT c FROM CardEntity c WHERE c.worldId = :worldId AND c.id = :id AND c.deleted = false")
    Optional<CardEntity> findByWorldIdAndIdAndNotDeleted(@Param("worldId") Long worldId, @Param("id") Long id);
}
