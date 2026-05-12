package io.github.carlosyamanaka.cyphvv.application.core.domain;

import java.util.List;

public class CardRelationship {
    private Long id;
    private String name;
    private Long originCardId;
    private List<CardRelationshipTarget> targets;
    private Boolean deleted;

    public CardRelationship(Long id, String name, Long originCardId, List<CardRelationshipTarget> targets, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.originCardId = originCardId;
        this.targets = targets;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getOriginCardId() {
        return originCardId;
    }

    public List<CardRelationshipTarget> getTargets() {
        return targets;
    }

    public Boolean getDeleted() {
        return deleted;
    }
}
