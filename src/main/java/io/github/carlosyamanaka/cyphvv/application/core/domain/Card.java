package io.github.carlosyamanaka.cyphvv.application.core.domain;

import java.time.OffsetDateTime;
import java.util.List;

public class Card {
    private Long id;
    private Long worldId;
    private Long cardTypeId;
    private String cardName;
    private String imageUrl;
    private List<String> aliases;
    private List<CardSection> sections;
    private List<CardRelationship> relationships;
    private OffsetDateTime createdAt;
    private Boolean deleted;
    private OffsetDateTime deletedAt;

    public Card(Long id, Long worldId, Long cardTypeId, String cardName,
            String imageUrl, List<String> aliases, List<CardSection> sections,
            List<CardRelationship> relationships,
            OffsetDateTime createdAt, Boolean deleted, OffsetDateTime deletedAt) {
        this.id = id;
        this.worldId = worldId;
        this.cardTypeId = cardTypeId;
        this.cardName = cardName;
        this.imageUrl = imageUrl;
        this.aliases = aliases;
        this.sections = sections;
        this.relationships = relationships;
        this.createdAt = createdAt;
        this.deleted = deleted;
        this.deletedAt = deletedAt;
    }

    public void delete() {
        this.deleted = true;
        this.deletedAt = OffsetDateTime.now();
        this.aliases = java.util.Collections.emptyList();
    }

    public void addAlias(String alias) {
        if (alias == null || alias.isBlank()) {
            return;
        }
        if (this.aliases == null) {
            this.aliases = new java.util.ArrayList<>();
        }
        if (!this.aliases.contains(alias)) {
            this.aliases.add(alias);
        }
    }

    public void removeAlias(String alias) {
        if (this.aliases != null) {
            this.aliases.remove(alias);
        }
    }

    public void updateName(String cardName) {
        this.cardName = cardName;
    }

    // Getters
    public Long getId() { return id; }
    public Long getWorldId() { return worldId; }
    public Long getCardTypeId() { return cardTypeId; }
    public String getCardName() { return cardName; }
    public String getImageUrl() { return imageUrl; }
    public List<String> getAliases() { return aliases; }
    public List<CardSection> getSections() { return sections; }
    public List<CardRelationship> getRelationships() { return relationships; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public Boolean getDeleted() { return deleted; }
    public OffsetDateTime getDeletedAt() { return deletedAt; }
}
