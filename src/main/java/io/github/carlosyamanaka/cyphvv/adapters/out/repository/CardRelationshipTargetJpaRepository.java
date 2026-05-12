package io.github.carlosyamanaka.cyphvv.adapters.out.repository;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardRelationshipTargetEntity;
import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardRelationshipTargetId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRelationshipTargetJpaRepository extends JpaRepository<CardRelationshipTargetEntity, CardRelationshipTargetId> {
    List<CardRelationshipTargetEntity> findByRelationshipId(Long relationshipId);
    void deleteByRelationshipId(Long relationshipId);
}
