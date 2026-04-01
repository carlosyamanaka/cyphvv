package io.github.carlosyamanaka.cyphvv.application.core.domain;

import java.time.OffsetDateTime;

public class CardType {

    private Long id;
    private Long worldId;
    private String cardTypeName;
    private OffsetDateTime createdAt;
    private Boolean deleted;
    private OffsetDateTime deletedAt;

    public CardType(Long id, Long worldId, String cardTypeName, OffsetDateTime createdAt, Boolean deleted,
            OffsetDateTime deletedAt) {
        this.id = id;
        this.worldId = worldId;
        this.cardTypeName = cardTypeName;
        this.createdAt = createdAt;
        this.deleted = deleted;
        this.deletedAt = deletedAt;
    }

    public void updateName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public void delete() {
        this.deleted = true;
        this.deletedAt = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getWorldId() {
        return worldId;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public OffsetDateTime getDeletedAt() {
        return deletedAt;
    }
}
