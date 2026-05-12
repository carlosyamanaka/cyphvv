package io.github.carlosyamanaka.cyphvv.application.core.domain;

public class CardRelationshipTarget {
    private Long targetCardId;

    public CardRelationshipTarget(Long targetCardId) {
        this.targetCardId = targetCardId;
    }

    public Long getTargetCardId() {
        return targetCardId;
    }
}
