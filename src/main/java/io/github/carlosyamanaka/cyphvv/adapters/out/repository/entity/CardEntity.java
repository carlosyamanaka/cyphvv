package io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "card")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "world_id", nullable = false)
    private Long worldId;

    @Column(name = "card_type_id", nullable = false)
    private Long cardTypeId;

    @Column(name = "img_url")
    private String imageUrl;

    @ElementCollection
    @CollectionTable(name = "card_aliases", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "alias")
    private List<String> aliases;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    private Boolean deleted;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    public CardEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getWorldId() { return worldId; }
    public void setWorldId(Long worldId) { this.worldId = worldId; }

    public Long getCardTypeId() { return cardTypeId; }
    public void setCardTypeId(Long cardTypeId) { this.cardTypeId = cardTypeId; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public List<String> getAliases() { return aliases; }
    public void setAliases(List<String> aliases) { this.aliases = aliases; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public Boolean getDeleted() { return deleted; }
    public void setDeleted(Boolean deleted) { this.deleted = deleted; }

    public OffsetDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(OffsetDateTime deletedAt) { this.deletedAt = deletedAt; }
}
