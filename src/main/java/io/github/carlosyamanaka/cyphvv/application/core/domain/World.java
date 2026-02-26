package io.github.carlosyamanaka.cyphvv.application.core.domain;

import java.time.OffsetDateTime;

public class World {

    private Long id;
    private String userId;
    private OffsetDateTime createdAt;
    private Boolean deleted;
    private OffsetDateTime deletedAt;
    private String worldName;

    public World(Long id, String userId, OffsetDateTime createdAt, Boolean deleted, OffsetDateTime deletedAt, String worldName) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.deleted = deleted;
        this.deletedAt = deletedAt;
        this.worldName = worldName;
    }

    // Business Logic Method
    public void delete() {
        this.deleted = true;
        this.deletedAt = OffsetDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public String getUserId() { return userId; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public Boolean getDeleted() { return deleted; }
    public OffsetDateTime getDeletedAt() { return deletedAt; }
    public String getWorldName(){ return worldName; }
}