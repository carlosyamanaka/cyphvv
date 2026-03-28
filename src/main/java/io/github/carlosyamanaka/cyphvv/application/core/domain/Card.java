package io.github.carlosyamanaka.cyphvv.application.core.domain;

import java.time.OffsetDateTime;
import java.util.List;

public class Card {
    private Long id;
    private Long worldId;
    private Long cardTypeId;
    private String imageUrl;
    private List<String> aliases;
    private OffsetDateTime createdAt;
    private Boolean deleted;
    private OffsetDateTime deletedAt;

    public Card(Long id, Long worldId, Long cardTypeId, String imageUrl, List<String> aliases,
                OffsetDateTime createdAt, Boolean deleted, OffsetDateTime deletedAt) {
        this.id = id;
        this.worldId = worldId;
        this.cardTypeId = cardTypeId;
        this.imageUrl = imageUrl;
        this.aliases = aliases;
        this.createdAt = createdAt;
        this.deleted = deleted;
        this.deletedAt = deletedAt;
    }

    public void delete() {
        this.deleted = true;
        this.deletedAt = OffsetDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public Long getWorldId() { return worldId; }
    public Long getCardTypeId() { return cardTypeId; }
    public String getImageUrl() { return imageUrl; }
    public List<String> getAliases() { return aliases; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public Boolean getDeleted() { return deleted; }
    public OffsetDateTime getDeletedAt() { return deletedAt; }
}
