package io.github.carlosyamanaka.cyphvv.adapters.out.repository;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.WorldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorldJpaRepository extends JpaRepository<WorldEntity, Long> {
    @Query("SELECT w FROM WorldEntity w WHERE w.userId = :userId AND w.deleted = false ORDER BY w.createdAt DESC")
    List<WorldEntity> findByUserIdAndNotDeleted(@Param("userId") String userId);

    @Query("SELECT w FROM WorldEntity w WHERE w.id = :id AND w.deleted = false")
    Optional<WorldEntity> findByIdAndNotDeleted(@Param("id") Long id);
}