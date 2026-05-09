package io.github.carlosyamanaka.cyphvv.application.core.domain;

import java.time.OffsetDateTime;

public class CardSection {

    private Long id;
    private Long cardId;
    private String type;
    private String content;
    private OffsetDateTime createdAt;
    private Boolean deleted;

    public CardSection(Long id, Long cardId, String type, String content,
            OffsetDateTime createdAt, Boolean deleted) {
        this.id = id;
        this.cardId = cardId;
        this.type = type;
        this.content = content;
        this.createdAt = createdAt;
        this.deleted = deleted;
    }

    public Long getId() { return id; }
    public Long getCardId() { return cardId; }
    public String getType() { return type; }
    public String getContent() { return content; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public Boolean getDeleted() { return deleted; }
}
