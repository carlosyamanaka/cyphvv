package io.github.carlosyamanaka.cyphvv.application.core.domain;

import java.time.OffsetDateTime;

public class CardType {

    private Long id;
    private Long worldId;
    private String cardTypeName;
    private String iconType;
    private OffsetDateTime createdAt;
    private Boolean deleted;
    private OffsetDateTime deletedAt;

    public CardType(Long id, Long worldId, String cardTypeName, String iconType, OffsetDateTime createdAt, Boolean deleted,
            OffsetDateTime deletedAt) {
        this.id = id;
        this.worldId = worldId;
        this.cardTypeName = cardTypeName;
        this.iconType = iconType;
        this.createdAt = createdAt;
        this.deleted = deleted;
        this.deletedAt = deletedAt;
    }

    public void update(String cardTypeName, String iconType) {
        this.cardTypeName = cardTypeName;
        this.iconType = iconType;
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

    public String getIconType() {
        return iconType;
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
