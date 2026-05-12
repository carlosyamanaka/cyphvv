package io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "card_relationship_target")
@IdClass(CardRelationshipTargetId.class)
public class CardRelationshipTargetEntity {

    @Id
    @Column(name = "card_relationship_id")
    private Long relationshipId;

    @Id
    @Column(name = "target_card_id")
    private Long targetCardId;

    private Boolean deleted = false;

    public CardRelationshipTargetEntity() {
    }

    public CardRelationshipTargetEntity(Long relationshipId, Long targetCardId) {
        this.relationshipId = relationshipId;
        this.targetCardId = targetCardId;
    }

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }

    public Long getTargetCardId() {
        return targetCardId;
    }

    public void setTargetCardId(Long targetCardId) {
        this.targetCardId = targetCardId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
