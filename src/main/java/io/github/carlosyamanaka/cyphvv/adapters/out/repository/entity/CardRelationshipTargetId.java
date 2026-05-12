package io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity;

import java.io.Serializable;
import java.util.Objects;

public class CardRelationshipTargetId implements Serializable {
    private Long relationshipId;
    private Long targetCardId;

    public CardRelationshipTargetId() {}

    public CardRelationshipTargetId(Long relationshipId, Long targetCardId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardRelationshipTargetId that = (CardRelationshipTargetId) o;
        return Objects.equals(relationshipId, that.relationshipId) && Objects.equals(targetCardId, that.targetCardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relationshipId, targetCardId);
    }
}
