package io.github.carlosyamanaka.cyphvv.application.core.domain;

import java.time.OffsetDateTime;

public class World {

    private Long id;
    private String userId;
    private OffsetDateTime createdAt;
    private Boolean deleted;
    private OffsetDateTime deletedAt;

    public World(Long id, String userId, OffsetDateTime createdAt, Boolean deleted, OffsetDateTime deletedAt) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.deleted = deleted;
        this.deletedAt = deletedAt;
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
}