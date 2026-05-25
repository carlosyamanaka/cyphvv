package io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "card_relationship")
public class CardRelationshipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "origin_card_id", nullable = false)
    private Long originCardId;

    private Boolean deleted = false;

    // Targets são gerenciados manualmente pelo adapter via batch queries
    // (sem @OneToMany para evitar lazy loading e N+1 implícito)
    @Transient
    private List<CardRelationshipTargetEntity> targets;

    public CardRelationshipEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOriginCardId() {
        return originCardId;
    }

    public void setOriginCardId(Long originCardId) {
        this.originCardId = originCardId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<CardRelationshipTargetEntity> getTargets() {
        return targets;
    }

    public void setTargets(List<CardRelationshipTargetEntity> targets) {
        this.targets = targets;
    }
}
